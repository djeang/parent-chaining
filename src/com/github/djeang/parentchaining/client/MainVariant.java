package com.github.djeang.parentchaining.client;

import com.github.djeang.parentchaining.api.Html;
import com.github.djeang.parentchaining.api.TagNode;

import java.util.function.Consumer;

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
                    .apply(addRow("Jill", "Smith", "50"))
                    .apply(addRow("Eve", "Jackson", "94")).__
                .apply(MainVariant::addEnding).__;
        System.out.println(html);
    }

    static void addEnding(TagNode tagNode) {
        tagNode
            .div().attr("style", "bold")
                .text("This is the end of this page.");
    }

    static Consumer<TagNode<?>> addRow(String firstname, String lastname, String age) {
        return tagNode -> tagNode
                .tr()
                    .td().text(firstname).__
                    .td().text(lastname).__
                    .td().text(age);
    }
}
