package org.kyll.myserver.business.meaord.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2014-11-17 9:22
 */
@Entity
@Table(name = "MEAORD_DISHES")
public class MeaordDishes implements Serializable {
	private Long id;
	private String name;
	private String description;
	private String type;
	private Double price;
	private Integer sort;
	private MeaordRestaurant meaordRestaurant;

	public MeaordDishes() {
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

	@Column(name = "TYPE_")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "PRICE_")
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "SORT_")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESTAURANT_ID_")
	public MeaordRestaurant getMeaordRestaurant() {
		return meaordRestaurant;
	}

	public void setMeaordRestaurant(MeaordRestaurant meaordRestaurant) {
		this.meaordRestaurant = meaordRestaurant;
	}
}
