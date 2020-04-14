package com.github.djeang.parentchaining.client;

import com.github.djeang.parentchaining.api.Html;

public class Main {

    public static void main(String[] args) {
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

        System.out.println(html);
    }
}
