package org.kyll.myserver.business.meaord.vo;

import org.kyll.myserver.base.Vo;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * User: Kyll
 * Date: 2014-11-17 9:33
 */
public class MeaordDishesVo implements Vo<Long> {
	private Long id;
	private String name;
	private String description;
	private String type;
	private Double price;
	private Integer sort;
	private CommonsMultipartFile[] photo;
	private Long meaordRestaurantId;
	private String meaordRestaurantName;

	public MeaordDishesVo() {
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public CommonsMultipartFile[] getPhoto() {
		return photo;
	}

	public void setPhoto(CommonsMultipartFile[] photo) {
		this.photo = photo;
	}

	public Long getMeaordRestaurantId() {
		return meaordRestaurantId;
	}

	public void setMeaordRestaurantId(Long meaordRestaurantId) {
		this.meaordRestaurantId = meaordRestaurantId;
	}

	public String getMeaordRestaurantName() {
		return meaordRestaurantName;
	}

	public void setMeaordRestaurantName(String meaordRestaurantName) {
		this.meaordRestaurantName = meaordRestaurantName;
	}
}
