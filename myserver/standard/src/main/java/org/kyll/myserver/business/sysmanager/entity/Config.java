package org.kyll.myserver.business.sysmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2014-12-02 16:27
 */
@Entity
@Table(name = "SYS_CONFIG")
public class Config implements Serializable {
	private Long id;
	private String key;
	private String value;

	public Config() {
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

	@Column(name = "KEY_")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Column(name = "VALUE_")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
