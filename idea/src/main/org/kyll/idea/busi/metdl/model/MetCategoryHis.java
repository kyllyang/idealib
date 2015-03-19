package org.kyll.idea.busi.metdl.model;

public class MetCategoryHis {
	private String id;
	private MetCategory metCategory;
	private String mergeName;

	public MetCategoryHis() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MetCategory getMetCategory() {
		return metCategory;
	}

	public void setMetCategory(MetCategory metCategory) {
		this.metCategory = metCategory;
	}

	public String getMergeName() {
		return mergeName;
	}

	public void setMergeName(String mergeName) {
		this.mergeName = mergeName;
	}
}
