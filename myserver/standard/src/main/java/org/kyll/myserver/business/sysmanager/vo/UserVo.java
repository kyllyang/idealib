package org.kyll.myserver.business.sysmanager.vo;

import org.kyll.myserver.base.Vo;

/**
 * User: Kyll
 * Date: 2014-11-07 13:52
 */
public class UserVo implements Vo<Long> {
	private Long id;
	private String username;
	private String password;
	private String email;
	private Integer freeze;
	private Integer sort;

	public UserVo() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getFreeze() {
		return freeze;
	}

	public void setFreeze(Integer freeze) {
		this.freeze = freeze;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
