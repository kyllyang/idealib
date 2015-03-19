package org.kyll.idea.busi.metdl.model;

import java.io.Serializable;

public class MetUrl implements Serializable {
	private String id;
	private String url;
	private Met met;

	public MetUrl() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Met getMet() {
		return met;
	}

	public void setMet(Met met) {
		this.met = met;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MetUrl metUrl = (MetUrl) o;

		return url.equals(metUrl.url);
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}
}
