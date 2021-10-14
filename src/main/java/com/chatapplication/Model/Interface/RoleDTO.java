package com.chatapplication.Model.Interface;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {
	private Long id;
	private String name;
	private String description;
	private String createdDate;
	private String updatedDate;
	private boolean status;
	private List<PrivilegeDTO> privileges = new ArrayList<>();
}
