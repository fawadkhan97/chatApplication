package com.chatapplication.Model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "chats")
@Data
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private String question;
	@Column(nullable = false)
	private String answer;
	private String createdDate;
	private String updatedDate;
	private boolean status;
}
