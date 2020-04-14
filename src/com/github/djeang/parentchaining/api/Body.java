package com.github.djeang.parentchaining.api;

public class Body extends TagNode<Html> {

    private Body(Html parent) {
        super(parent, "body");
    }

    // To be used in parent-chaining context
    public static Body ofParent(Html parent) {
        return new Body(parent);
    }

    // To be used outside parent-chaining context
    public static Body of() {
        return new Body(null);
    }

}
