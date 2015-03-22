package org.kyll.myserver.business.sysmanager.vo;

import org.kyll.myserver.base.Vo;

/**
 * User: Kyll
 * Date: 2014-11-09 06:52
 */
public class DictVo implements Vo<Long> {
	private Long id;
	private Long parentId;
	private String parentName;
	private String invokeCode;
	private String name;
	private Integer sort;

	public DictVo() {
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getInvokeCode() {
		return invokeCode;
	}

	public void setInvokeCode(String invokeCode) {
		this.invokeCode = invokeCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
