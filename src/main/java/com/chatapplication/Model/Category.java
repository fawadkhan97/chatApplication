package com.chatapplication.Model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String name;
	private String createdDate;
	private String updatedDate;

	public Category(String name, String createdDate, String updatedDate) {
		this.name = name;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
}
