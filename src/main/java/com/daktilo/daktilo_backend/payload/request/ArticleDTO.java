package com.daktilo.daktilo_backend.payload.request;

import com.daktilo.daktilo_backend.entity.Author;
import com.daktilo.daktilo_backend.entity.Category;
import com.daktilo.daktilo_backend.entity.Comment;
import com.daktilo.daktilo_backend.entity.Tag;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ArticleDTO {
    private Set<Category> categories;

    private Set<TagDTO> tags;

    private UUID authorId;

    private Timestamp datePosted;

    private String articleTitle;

    private String articleContent;

    private boolean commentStatus;

    private boolean active;

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public Timestamp getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Timestamp datePosted) {
        this.datePosted = datePosted;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public boolean isCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(boolean commentStatus) {
        this.commentStatus = commentStatus;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
