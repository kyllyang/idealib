package org.kyll.common.model;

import java.io.Serializable;

public class Binary implements Serializable {
	private String id;
	private byte[] content;

	public Binary() {
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
}
