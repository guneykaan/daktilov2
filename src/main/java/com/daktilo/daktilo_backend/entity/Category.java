package com.daktilo.daktilo_backend.entity;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="CATEGORY")
public class Category {
    @Id
    @Column(name="category_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, name="category_name")
    private String categoryName;

    @Column(name="category_desc")
    private String categoryDesc;

    @ManyToMany(mappedBy="categories", fetch=FetchType.LAZY)
    private Set<Article> articles;

    public Category(){

    }

    public Category(String categoryName, String categoryDesc, Set<Article> articles) {
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
        this.articles = articles;
    }

    public Category(String categoryName, String categoryDesc) {
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) && categoryName.equals(category.categoryName) && categoryDesc.equals(category.categoryDesc) && Objects.equals(articles, category.articles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoryName, categoryDesc, articles);
    }
}
