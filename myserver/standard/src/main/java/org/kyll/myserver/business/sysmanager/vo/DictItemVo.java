package org.kyll.myserver.business.sysmanager.vo;

import org.kyll.myserver.base.Vo;

/**
 * User: Kyll
 * Date: 2015-02-05 15:55
 */
public class DictItemVo implements Vo<Long> {
	private Long id;
	private Long dictId;
	private String key;
	private String value;
	private Integer sort;

	public DictItemVo() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDictId() {
		return dictId;
	}

	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
