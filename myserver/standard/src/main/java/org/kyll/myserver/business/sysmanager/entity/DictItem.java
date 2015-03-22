package org.kyll.myserver.business.sysmanager.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Kyll
 * Date: 2015-02-05 14:52
 */
@Entity
@Table(name = "SYS_DICT_ITEM")
public class DictItem implements Serializable {
	private Long id;
	private Dict dict;
	private String key;
	private String value;
	private Integer sort;

	public DictItem() {
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DICT_ID_")
	public Dict getDict() {
		return dict;
	}

	public void setDict(Dict dict) {
		this.dict = dict;
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

	@Column(name = "SORT_")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
