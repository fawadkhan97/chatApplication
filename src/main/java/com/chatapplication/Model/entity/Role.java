package com.chatapplication.Model.entity;

import lombok.Data;
import org.intellij.lang.annotations.Identifier;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "roles")
@Entity
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true)
	private String name;
	private String CreatedDate;
	private String UpdatedDate;
	private boolean status;

	@ManyToMany(targetEntity = Permission.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(name = "roles_permission", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = {
			@JoinColumn(name = "permission_id") })
	private List<Permission> permissions = new ArrayList<>();

}
