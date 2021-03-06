package com.chatapplication.Model.Interface;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
	private Long id;
	private String name;
	private boolean status;
	private String createdDate;
	private String updatedDate;
}
