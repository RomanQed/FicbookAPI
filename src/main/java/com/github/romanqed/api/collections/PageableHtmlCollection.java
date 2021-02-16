package com.github.romanqed.api.collections;

public class PageableHtmlCollection<T> extends HtmlCollection<T> {
    private final int maxPageNumber;

    public PageableHtmlCollection(int maxPageNumber) {
        super();
        this.maxPageNumber = Math.max(-1, maxPageNumber);
    }

    public int getMaxPageNumber() {
        return maxPageNumber;
    }
}
