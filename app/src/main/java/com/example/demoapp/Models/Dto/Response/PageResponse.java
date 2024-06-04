package com.example.demoapp.Models.Dto.Response;

import java.io.Serializable;

public class PageResponse<T> implements Serializable {
    private int page;
    private int size;
    private int total;
    private T items;

    public PageResponse(int page, int size, int total, T items) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.items = items;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public T getContent() {
        return items;
    }
}
