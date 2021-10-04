package com.chatApplication.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, name = "user_name", nullable = false)
    private String userName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String cnic;
    @Column(nullable = false)
    private int age;

    @OneToMany(targetEntity = Chat.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false)
    private List<Chat> chats = new ArrayList<>();

    @ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_categories", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {
            @JoinColumn(name = "category_id")})
    private List<Category> categoryList;


    public User(String userName, String email, String password, String cnic, int age, List<Chat> chats, List<Category> categoryList) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.cnic = cnic;
        this.age = age;
        this.chats = chats;
        this.categoryList = categoryList;
    }


}
