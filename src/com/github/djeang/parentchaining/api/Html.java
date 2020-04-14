package com.github.djeang.parentchaining.api;

public class Html {

    private final Head head = new Head(this);

    private final Body body = Body.ofParent(this);

    public Head head() {
        return head;
    }

    public Body body() {
        return body;
    }

}
