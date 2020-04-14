package com.github.djeang.parentchaining.api;

import java.util.LinkedList;
import java.util.List;

public class Head {

    public final Html __;

    private String title;

    private final List<Meta> metas = new LinkedList<>();

    Head(Html parent) {
        this.__ = parent;
    }

    public Head title(String title) {
        this.title = title;
        return this;
    }

    public Meta meta() {
        Meta meta = new Meta(this);
        this.metas.add(meta);
        return meta;
    }


    public static class Meta extends TagNode<Head> {

        private Meta(Head parent) {
            super(parent, "meta");
        }

        public Meta charset(String charset) {
            return (Meta) this.attr("charset", charset);
        }

        public Meta content(String name, String content) {
            return (Meta) this.attr("name", name).attr("content", content);
        }

    }

    @Override
    public String toString() {
        StringBuilder metaBuilder = new StringBuilder();
        metas.forEach(metaBuilder::append);
        return String.format("<head><title>%s</title>%s</head>", title, metaBuilder);
    }
}
