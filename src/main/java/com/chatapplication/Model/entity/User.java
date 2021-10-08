package com.chatapplication.Model.entity;

import com.chatapplication.Model.entity.Category;
import com.chatapplication.Model.entity.Chat;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
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
	private String createdDate;
	private String updatedDate;

	@OneToMany(targetEntity = Chat.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "user_id", nullable = false)
	private List<Chat> chats = new ArrayList<>();

	@ManyToMany(targetEntity = Category.class, cascade = CascadeType.MERGE)
	@JoinTable(name = "user_category", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	private List<Category> categories = new ArrayList<>();

	public User(String userName, String email, String password, String cnic, int age, String createdDate,
			String updatedDate, List<Chat> chats, List<Category> categories) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.cnic = cnic;
		this.age = age;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.chats = chats;
		this.categories = categories;
	}

	public User() {
	}
}
