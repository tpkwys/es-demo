package com.aaron.esdemo.entity;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteWorkbook;

public class Article {
    private Long id;

    private String text;

    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
