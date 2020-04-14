package com.github.djeang.parentchaining.api;

public class TextNode implements Node {

    private final String text;

    public TextNode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
