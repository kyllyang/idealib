package org.kyll.myserver.business.sysmanager.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * User: Kyll
 * Date: 2014-11-05 13:04
 */
@Entity
@Table(name = "SYS_ROLE")
public class Role implements Serializable {
	private Long id;
	private String name;
	private String description;
	private Integer sort;
	private Set<User> userSet;
	private Set<Module> moduleSet;

	public Role() {
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

	@Column(name = "NAME_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "DESCRIPTION_")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "SORT_")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@ManyToMany(mappedBy = "roleSet")
	public Set<User> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<User> userSet) {
		this.userSet = userSet;
	}

	@ManyToMany
	@JoinTable(name = "sys_role_module", joinColumns = {@JoinColumn(name = "role_id_")}, inverseJoinColumns = {@JoinColumn(name = "module_id_")})
	public Set<Module> getModuleSet() {
		return moduleSet;
	}

	public void setModuleSet(Set<Module> moduleSet) {
		this.moduleSet = moduleSet;
	}
}
