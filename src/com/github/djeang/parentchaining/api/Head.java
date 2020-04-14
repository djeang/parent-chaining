package com.github.djeang.parentchaining.api;

import java.util.LinkedList;
import java.util.List;

public class Head {

    public final Html __;

    private String title;

    private final Metas metas = new Metas(this);

    Head(Html parent) {
        this.__ = parent;
    }

    public Head title(String title) {
        this.title = title;
        return this;
    }

    public Metas meta() {
        return this.metas;
    }



    public static class Metas {

        public final Head __;

        private final List<Meta> items = new LinkedList<>();

        private Metas(Head __) {
            this.__ = __;
        }

        public Metas charset(String charset) {
            items.add(new Meta(charset, null, null));
            return this;
        }

        public Metas content(String name, String content) {
            items.add(new Meta(null, name, content));
            return this;
        }
    }


    public static class Meta {

        private final String charset;

        private final String name;

        private final String content;

        Meta(String charset, String name, String content) {
            this.charset = charset;
            this.name = name;
            this.content = content;
        }

    }
}
