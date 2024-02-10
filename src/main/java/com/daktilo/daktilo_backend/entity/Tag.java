package com.daktilo.daktilo_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="tag", uniqueConstraints = {
        @UniqueConstraint(columnNames={"tag_name"})
})
public class Tag {
    //Todo
    @Id
    @Column(name="tag_name")
    private String tagName;

    @JsonIgnore
    @ManyToMany(fetch= FetchType.LAZY, mappedBy="tags")
    @BatchSize(size=100)
    private List<Article> articles;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagName.equals(tag.tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName);
    }
}
