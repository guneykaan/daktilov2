package com.daktilo.daktilo_backend.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;
import java.util.List;

@Table(name="author", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"author_name"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class Author {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;
    @Column(name="author_name")
    private String authorName;
    @Column(name="email")
    //TODO validation
    private String authorEmail;
    @Column(name="accountStatus")
    private boolean authorAccountStatus;
    @Column(name="username")
    private String username;

    @Column(name="password")
    //TODO revisit w security changes
    private String password;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "author", orphanRemoval = false)
    private List<Article> articles;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public boolean getAuthorAccountStatus() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return authorAccountStatus == author.authorAccountStatus && Objects.equals(id, author.id) &&
                authorName.equals(author.authorName) && authorEmail.equals(author.authorEmail) &&
                username.equals(author.username) && password.equals(author.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorName, authorEmail, authorAccountStatus, username, password);
    }
}
