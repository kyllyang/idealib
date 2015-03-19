package org.kyll.common.base;

import java.io.Serializable;
import java.util.Date;

public class Vo implements Serializable {
	protected Long id;
	protected Integer sort;
	protected Date createTime;
	protected Date modifyTime;

	public Vo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Vo vo = (Vo) o;

		return id.equals(vo.id);

	}

	public int hashCode() {
		return id.hashCode();
	}
}
