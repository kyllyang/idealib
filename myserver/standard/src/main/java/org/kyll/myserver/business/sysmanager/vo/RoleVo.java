package org.kyll.myserver.business.sysmanager.vo;

import org.kyll.myserver.base.Vo;

/**
 * User: Kyll
 * Date: 2014-11-10 14:43
 */
public class RoleVo implements Vo<Long> {
	private Long id;
	private String name;
	private String description;
	private Integer sort;

	public RoleVo() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
