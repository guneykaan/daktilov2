package com.daktilo.daktilo_backend.entity;

import com.daktilo.daktilo_backend.constants.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="users", uniqueConstraints = {
        @UniqueConstraint(columnNames={"username"}),
        @UniqueConstraint(columnNames={"email"}),
        @UniqueConstraint(columnNames = {"phoneNumber"})
})
public class User implements UserDetails {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="password")
    //TODO revisit w security changes
    private String password;

    @Column(name="username")
    private String username;

    @Column(name="email")
    private String email;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @Column(name="date_joined")
    private Long dateJoined;

    @OneToMany(mappedBy="user")
    private List<Comment> commentList;

    @Column(name="NONEXPIRED")
    private boolean isAccountNonExpired=true;

    @Column(name="NONLOCKED")
    private boolean isAccountNonLocked=true;

    @Column(name="CREDNONEXPIRED")
    private boolean isCredentialsNonExpired=true;

    @Column(name="ENABLED")
    private boolean isEnabled=true;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "author", orphanRemoval = false)
    private List<Article> articles;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }


    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        isAccountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Long dateJoined) {
        this.dateJoined = dateJoined;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && password.equals(user.password) && username.equals(user.username) && email.equals(user.email) && phoneNumber.equals(user.phoneNumber) && dateJoined.equals(user.dateJoined) && Objects.equals(commentList, user.commentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, password, username, email, phoneNumber, dateJoined, commentList);
    }
}
