package org.kyll.myserver.business.meaord;

/**
 * User: Kyll
 * Date: 2014-11-14 13:21
 */
public class QueryCondition {
	private Long restaurantId;
	private String name;
	private String linkman;
	private String phone;
	private String type;

	public QueryCondition() {
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
