package org.kyll.common.paginated;

import java.util.List;

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
