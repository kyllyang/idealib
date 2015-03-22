package org.kyll.myserver.business.sysmanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * User: Kyll
 * Date: 2014-11-05 12:49
 */
@Entity
@Table(name = "SYS_USER")
public class User implements Serializable {
	private Long id;
	private String username;
	private String password;
	private String email;
	private Integer freeze;
	private Integer sort;
	Set<Role> roleSet;

	public User() {
	}

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "ID_")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "USERNAME_")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD_")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "EMAIL_")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "FREEZE_")
	public Integer getFreeze() {
		return freeze;
	}

	public void setFreeze(Integer freeze) {
		this.freeze = freeze;
	}

	@Column(name = "SORT_")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@ManyToMany
	@JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id_")}, inverseJoinColumns = {@JoinColumn(name = "role_id_")})
	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}
}
