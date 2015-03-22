package org.kyll.myserver.base.paginated;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-05 14:00
 */
public class Dataset<T> {
	private Paginated paginated;
	private List<T> dataList;

	public Dataset() {
	}

	public Paginated getPaginated() {
		return paginated;
	}

	public void setPaginated(Paginated paginated) {
		this.paginated = paginated;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}
