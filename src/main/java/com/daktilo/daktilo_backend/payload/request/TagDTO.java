package com.daktilo.daktilo_backend.payload.request;

import java.util.UUID;

public class TagDTO {

    private String tagName;

    private UUID articleId;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
