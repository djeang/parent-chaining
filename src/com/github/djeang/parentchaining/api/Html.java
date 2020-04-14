package com.github.djeang.parentchaining.api;

public class Html {

    private final Head head = new Head(this);

    private final TagNode<Html> body = TagNode.ofParent(this, "body");

    public Head head() {
        return head;
    }

    public TagNode<Html> body() {
        return body;
    }

    @Override
    public String toString() {
        return String.format("<html>%s%s</html>", head, body);
    }
}
