package com.daktilo.daktilo_backend.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="TAG", uniqueConstraints = {
        @UniqueConstraint(columnNames={"tag_name"})
})
public class Tag {
    @Id
    @Column(name="tag_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @Column(name="tag_name")
    private String tagName;

    @ManyToMany(fetch= FetchType.LAZY, mappedBy="tags")
    private List<Article> articles;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
        return Objects.equals(id, tag.id) && tagName.equals(tag.tagName) && articles.equals(tag.articles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tagName, articles);
    }
}
