# Parent-Chaining Pattern     <img src="connection.png"/>

## Abstract

[Method chaining](https://martinfowler.com/dslCatalog/methodChaining.html) pattern has been around 
for years in Java and other language. It is widely used to implement [builder](https://martinfowler.com/dslCatalog/constructionBuilder.html) 
or [fluent interface](https://martinfowler.com/bliki/FluentInterface.html)
but not, as far as I know, to instantiate or modify a complete tree structure.

The issue is that the chain termination determines the instance on which the next modifier method will apply on.
For flat structures it is ok but for trees, navigation methods are necessary to go deeper or higher in the structure. 

The purpose of this article is to introduce the *Parent-Chaining* pattern. This pattern completes *method chaining* to 
achieve tree instantiation/modification with *tree-looking-like* code. 

The example shown here is implemented in Java, but it can apply to any statically typed language featuring generics. 
Full working code is available [here](src/com/github/djeang/parentchaining).

## Example

For developers, the most familiar tree structure is probably HTML DOM. So let's see how to create 
an HTML DOM instance with an API based on *Parent-Chaining* pattern.

```
Html html = new Html()
    .head()
        .title("Title of my document")
        .meta().charset("UTF-8").__
        .meta().content("description", "Parent-Chaining illustrator").__
        .meta().content("keywords", "design pattern, chaining, tree structure, Java").__
        .meta().content("author", "Jerome Angibaud").__.__
    .body()
        .text("This is a simple page for describing Parent-Chaining pattern.")
        .div().attr("style", "bold")
            .text("A first div displayed in bold.")
            .div()
                .img().attr("src", "fuse.png").attr("alt", "A fuse image.").__
                .text("This is a new table")
                .table().attr("style", "width:100%")
                    .tr()
                        .th().text("Firstname").__
                        .th().text("Lastname").__
                        .th().text("Age").__.__
                    .tr()
                        .td().text("Jill").__
                        .td().text("Smith").__
                        .td().text("50").__.__
                    .tr()
                        .td().text("Eve").__
                        .td().text("Jackson").__
                        .td().text("94").__.__.__.__.__
        .div()
            .text("This is the end of this page.").__.__;
```
As you see, the point of this pattern is to write quite readable code when dealing with tree structures 
(which is very common in computer science). Here, we were able to fulfill an entire HTML document in a single chained statement. 

You probably noticed `.__` termination returning the parent of the current object. 
This is the clue of *Parent-Chaining* pattern. 

Note that `_` (single underscore) is not a valid identifier in Java9+ anymore, so we chose `__` (double underscore).
Of course, we can choose any other valid field or method identifier to return parent but, `__` is nice as it looks 
like a language feature.

## Implementation

### Instantiating Root Class

Top element does not need mechanism to return parent, it instantiates its own children by passing 
itself as reference.

```
public class Html {

    private final Head head = new Head(this);

    private final TagNode<Html> body = TagNode.ofParent(this, "body");

    public Head head() {
        return head;
    }
    
    public TagNode<Html> body() {
        return body;
    }

}
```

### Instantiating Child Classes

For illustration purpose, `Head` and `Body` do not follow strictly the same design. `<head>` has its own type 
enforcing a restricted set of child tags (`<title>` or `<meta>`), while `<body>` and its children use a generic `TagNode` type.
 
The parent must be set once for all at instantiation time to benefit from `final` modifier. 
 
#### Child Classes Having a Unique Parent Type

`Head` parent is always `Html` so we can declare the parent with a non-generic type as shown below.
 
```
public class Head {

    public final Html __;  // For parent chaining

    private String title;

    private final List<Meta> metas = new LinkedList<>();

    Head(Html parent) {
        this.__ = parent;
    }
```

#### Child Classes Having Multiple Parent Types

In contrast, `TagNode` can have both `TagName`or `Html` as parent : we need generics properly to handle this case.

The `<P>` generic parameter stands for the type of the parent. Thanks to `of` factory methods, classes can be used 
inside or outside of a parent-chaining pattern.

```
public class TagNode<P> implements Node {  // P is the genreric type of the parent

    public final P __; // Parent for chaining

    private final String name;  // The tag name : body, div, img, ...

    ...

    protected TagNode(P parent, String name) {
        this.__ = parent;
        this.name = name;
    }

    // To be used in parent-chaining context
    public static <P> TagNode<P> ofParent(P parent, String name) {
        return new TagNode<>(parent, name);
    }

    // To be used outside parent-chaining context
    public static TagNode of(String name) {
        return new TagNode(null, name);
    }
    ...

```
 
### Appending Children

To let interface being navigable, setter/adder methods must return accurate types : 
* If a method sets or appends a leaf object (like a `String` or a `Date`) then ìt must return `this`. 
* If a method adds a navigable node as `TagNode`, it must return ther proper generified child type .

For example, to append children, `TagNode#child(tagName)` creates a child instance, adds it to its children 
then returns the child typed as `TagNode<TagNode<P>>`, `TagNote<T>` being the type of current object.

```
    public TagNode<TagNode<P>> child(String name) {  // add a tag with the specified name to childreen
        TagNode<TagNode<P>> child = TagNode.ofParent(this, name);
        this.children.add(child);
        return child;
    }

    public TagNode<P> text(String text) {
        this.children.add(new TextNode(text));
        return this;
    }

    public TagNode<TagNode<P>> div() {
        return child("div");
    }

    public TagNode<TagNode<P>> img() {
        return child("img");
    }

   ...
```

### Make it Dynamic

So far, we have only dealt with static content described "inline" the chained statement. 
To include dynamic content, we'll use Java functional consumers.

Just add such a `TagNode#apply(Consumer<TagNode>)` method taking a consumer to itself as a parameter :

```
public class TagNode<P> implements Node {

    ...
    
    // This method accepts the a TagNode consumer for delegation.
    public TagNode<P> apply(Consumer<TagNode<?>> consumer) {
        consumer.accept(this);
        return this;
    }
```

Now we can delegate entire part of the tree handling to simple methods.

``` Java
public class MainVariant {

    public static void main(String[] args) {
        Html html = new Html()
            .head()
                .title("Title of my document")
                .meta().charset("UTF-8").__.__
            .body()
                .table().attr("style", "width:100%")
                    .tr()
                        .th().text("Firstname").__
                        .th().text("Lastname").__
                        .th().text("Age").__.__
                    .apply(MainVariant::populateRows).__
                .div().attr("style", "bold")
                    .text("This is the end of this page.").__.__;
        System.out.println(html);
    }

    static void populateRows(TagNode table) { // Typically fetched from a database
        addRow(table, "Jill", "Smith", "50");
        addRow(table, "Eve", "Jackson", "94");
    }

    private static void addRow(TagNode<?> table, String firstname, String lastname, String age) {
        table
            .tr()
                .td().text(firstname).__
                .td().text(lastname).__
                .td().text(age);
    }
}
```
As you can see here, the Html tree is designed as a template. The addition of dynamic parts is delegated to simple 
Java methods.

## Conclusion

*Parent-Chaining* pattern is a solution to greatly improve code readability, at the cost of a few extra coding/complexity.

Potential applications are, among others, better DOM manipulation libraries, better generated code from XML/Json->Java tooling, 
or simply cleaner configuration code.

These projects are using *Parent-Chaining* pattern intensively :
* [Jeka](https://jeka.dev) version 0.9 : a 100% Java build tool.
* [vincer-dom](https://github.com/djeang/vincer-dom) : a DOM manipulation library


> Icons made by <a href="https://www.flaticon.com/authors/eucalyp" title="Eucalyp">Eucalyp</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a>
