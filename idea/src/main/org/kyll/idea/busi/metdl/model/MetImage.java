package org.kyll.idea.busi.metdl.model;

import java.io.Serializable;

public class MetImage implements Serializable {
	private String id;
	private byte[] content;

	public MetImage() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MetImage metImage = (MetImage) o;

		if (!id.equals(metImage.id)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
