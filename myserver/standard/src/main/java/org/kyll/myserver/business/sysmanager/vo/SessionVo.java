package org.kyll.myserver.business.sysmanager.vo;

/**
 * User: Kyll
 * Date: 2014-11-07 15:46
 */
public class SessionVo {
	private Long userId;
	private String username;

	public SessionVo() {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
