package com.daktilo.daktilo_backend.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="ARTICLE")
public class Article {
    @Id
    @Column(name="article_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID articleId;

    //sistemde çok fazla category bulunmasını beklemiyoruz
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CATEGORY_ARTICLE_MAP_TABLE",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="TAG_ARTICLE_MAP_TABLE",
    joinColumns = {@JoinColumn(name="article_id")},
    inverseJoinColumns = {@JoinColumn(name="tag_id")})
    private Set<Tag> tags;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="author_id")
    private Author author;

    //TODO
    //sadece haberin full sayfasını açmak istediği zaman hepsini çek.
    //yeni bir query koşmadan join ile almaya çalış
    @OneToMany(fetch=FetchType.LAZY,
            mappedBy = "article",
            cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Column(name="date_posted")
    private Timestamp datePosted;

    @Column(name="article_title")
    private String articleTitle;

    @Column(name="article_content")
    private String articleContent;

    //comment yapılabilir mi yapılamaz mı
    @Column(name="comment_status")
    private boolean commentStatus;

    //haberi yayından kaldırabilmek için kullanılacak.
    @Column(name="active")
    private boolean active;

    @Column(name="view_count")
    private Long viewCount;

    public UUID getArticleId() {
        return articleId;
    }

    public void setArticleId(UUID articleId) {
        this.articleId = articleId;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return commentStatus == article.commentStatus && active == article.active && Objects.equals(articleId, article.articleId) && Objects.equals(categories, article.categories) && Objects.equals(tags, article.tags) && author.equals(article.author) && Objects.equals(comments, article.comments) && datePosted.equals(article.datePosted) && articleTitle.equals(article.articleTitle) && articleContent.equals(article.articleContent) && viewCount.equals(article.viewCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(articleId, categories, tags, author, comments, datePosted, articleTitle, articleContent, commentStatus, active, viewCount);
    }
}
