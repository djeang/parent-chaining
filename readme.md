# Parent-Chaining Pattern

## Abstract

[Method chaining](https://martinfowler.com/dslCatalog/methodChaining.html) pattern has been around in 
for years in Java and others language. It is widely used to implement [builder](https://martinfowler.com/dslCatalog/constructionBuilder.html) 
or [fluent interface](https://martinfowler.com/bliki/FluentInterface.html)
but never, as far as I know, to instantiate or modify a complete tree structure.

The issue is that the chain termination determines the instance on which the next modifier method will apply on.
Navigation methods would be necessary to go deeper or higher in the tree structure. 

The purpose of this article is to introduce the *parent chaining* pattern that will complete the *method chaining* to 
achieve tree instantiation/modification with *tree-looking-like* code. 

The examples shown here are implemented in Java but it can apply to any statically typed languages featuring generics. 
Source code of these exemples are hosted in this repository.

## Exemple

Probably, the most popular tree structure for programers is the HTML DOM. So let's see how it would like to manupulate 
a HTML DOM instance with an API based on *Parent-Chaining* pattern.

```
Html html = new Html()
    .head()
        .title("Title of my document")
        .meta()
            .charset("UTF-8")
            .content("description", "Parent-Chaining illustrator")
            .content("keywords", "design pattern, chaining, tree structure, Java")
            .content("author", "Jerome Angibaud").__.__
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
