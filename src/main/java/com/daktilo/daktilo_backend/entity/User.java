package com.daktilo.daktilo_backend.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames={"username"}),
        @UniqueConstraint(columnNames={"email"}),
        @UniqueConstraint(columnNames = {"phoneNumber"})
})
public class User {

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
    private Date dateJoined;

    @OneToMany(mappedBy="user")
    private List<Comment> commentList;


    //TODO revisit
//    @Column(name="user_profile_pic")
//    private Blob userProfilePic;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
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

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    /*public Blob getUserProfilePic() {
        return userProfilePic;
    }

    public void setUserProfilePic(Blob userProfilePic) {
        this.userProfilePic = userProfilePic;
    }*/

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
