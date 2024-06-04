package com.example.demoapp.Models.Dto.Requests;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class PostCreationRequest implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;
    private String name;
    private String content;
    private List<Integer> listTagId;

    public PostCreationRequest(String name, String content, List<Integer> listTagId) {
        this.name = name;
        this.content = content;
        this.listTagId = listTagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getListTagId() {
        return listTagId;
    }

    public void setListTagId(List<Integer> listTagId) {
        this.listTagId = listTagId;
    }
}
