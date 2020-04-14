package com.github.djeang.parentchaining.api;

import com.sun.xml.internal.rngom.ast.builder.Div;
import javafx.scene.Parent;
import org.w3c.dom.Attr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TagNode<P> implements Node {

    public final P __; // Parent for chaining

    private final String name;

    private final List<Node> children = new LinkedList<>();

    private final Map<String, String> attrs = new HashMap<>();

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

    public TagNode<TagNode<P>> child(String name) {
        TagNode<TagNode<P>> child = TagNode.ofParent(this, name);
        this.children.add(child);
        return child;
    }


    public TagNode<P> text(String text) {
        this.children.add(new TextNode(text));
        return this;
    }

    public TagNode<P> attr(String name, String value) {
        this.attrs.put(name, value);
        return this;
    }

    public TagNode<TagNode<P>> div() {
        return child("div");
    }

    public TagNode<TagNode<P>> img() {
        return child("img");
    }

    public TagNode<TagNode<P>> table() {
        return child("table");
    }

    public TagNode<TagNode<P>> tr() {
        return child("tr");
    }

    public TagNode<TagNode<P>> th() {
        return child("th");
    }

    public TagNode<TagNode<P>> td() {
        return child("td");
    }


}
