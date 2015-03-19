package org.kyll.idea.busi.metdl.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Met implements Serializable {
	public static final String STATUS_NEW = "0";
	public static final String STATUS_DOWNLOADING = "1";
	public static final String STATUS_FINISHED = "2";
	public static final String STATUS_DELETE = "3";
	public static final String STATUS_UNFINISHED = "4";
	private String[] searchStatus;

	private String id;
	private String name;
	private Date uploadTime;
	private Date updateTime;
	private String status;
	private MetCategory metCategory;
	private List<MetUrl> metUrlList;
	private String url;
	private String imageUrl;
	private MetImage metImage;

	public Met() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MetUrl> getMetUrlList() {
		return metUrlList;
	}

	public void setMetUrlList(List<MetUrl> metUrlList) {
		this.metUrlList = metUrlList;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MetCategory getMetCategory() {
		return metCategory;
	}

	public void setMetCategory(MetCategory metCategory) {
		this.metCategory = metCategory;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public MetImage getMetImage() {
		return metImage;
	}

	public void setMetImage(MetImage metImage) {
		this.metImage = metImage;
	}

	public String[] getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String[] searchStatus) {
		this.searchStatus = searchStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Met met = (Met) o;

		return name.equals(met.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return "Met{" +
			"name='" + name + '\'' +
			'}';
	}
}
