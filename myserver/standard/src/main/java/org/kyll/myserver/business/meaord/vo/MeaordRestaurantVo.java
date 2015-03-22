package org.kyll.myserver.business.meaord.vo;

import org.kyll.myserver.base.Vo;

/**
 * User: Kyll
 * Date: 2014-11-14 13:37
 */
public class MeaordRestaurantVo implements Vo<Long> {
	private Long id;
	private String name;
	private String description;
	private String linkman;
	private String phone1;
	private String phone2;
	private Integer sort;

	public MeaordRestaurantVo() {
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

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
