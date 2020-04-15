package com.github.djeang.parentchaining.client;

import com.github.djeang.parentchaining.api.Html;
import com.github.djeang.parentchaining.api.TagNode;

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
                    .apply(MainVariant::populateRows).__
                .div().attr("style", "bold")
                    .text("This is the end of this page.").__.__;
        System.out.println(html);
    }

    static void populateRows(TagNode table) { // Typically fetch from databasze
        addRow(table, "Jill", "Smith", "50");
        addRow(table, "Eve", "Jackson", "94");
    }

    private static void addRow(TagNode<?> table, String firstname, String lastname, String age) {
        table
            .tr()
                .td().text(firstname).__
                .td().text(lastname).__
                .td().text(age);
    }
}
