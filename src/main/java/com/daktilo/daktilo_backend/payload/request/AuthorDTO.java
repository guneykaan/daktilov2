package com.daktilo.daktilo_backend.payload.request;

import com.daktilo.daktilo_backend.entity.Article;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

public class AuthorDTO {

    private String authorName;
    //TODO validation
    private String authorEmail;
    private boolean authorAccountStatus;
    private String username;

    private String password;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public boolean isAuthorAccountStatus() {
        return authorAccountStatus;
    }

    public void setAuthorAccountStatus(boolean authorAccountStatus) {
        this.authorAccountStatus = authorAccountStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
