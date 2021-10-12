package com.chatapplication.Model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
	@DateTimeFormat
	private String createdDate;
	@DateTimeFormat
	private String updatedDate;

}
