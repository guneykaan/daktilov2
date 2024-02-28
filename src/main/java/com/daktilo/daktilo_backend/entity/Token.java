package com.daktilo.daktilo_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TOKEN")
public class Token {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    public UUID id;

    @Column(name="TOKEN",unique = true)
    public String token;

    @Column(name="TOKEN_TYPE")
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @Column(name="REVOKED")
    public boolean revoked;

    @Column(name="EXPIRED")
    public boolean expired;

    //rethink, graph/subgraphs?
    //TODO neden bu lazyler istediğimiz gibi çalışmıyor?
    //TODO configte bi salaklık var.
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}