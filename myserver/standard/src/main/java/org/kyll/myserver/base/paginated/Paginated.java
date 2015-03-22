package org.kyll.myserver.base.paginated;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-05 13:59
 */
public class Paginated {
	public static final Integer DEFAULT_STARTRECORD = 0;
	public static final Integer DEFAULT_MAXRECORD = 20;
	public static final Integer DEFAULT_DUEPAGE = 4;

	private Integer totalRecord;
	private Integer totalPage;
	private Integer currentRecord;
	private Integer currentPage;
	private Integer firstPage;
	private Integer lastPage;
	private Integer prevPage;
	private Integer nextPage;
	private Integer prevOnePage;
	private Integer nextOnePage;
	private Integer duePage;
	private Integer startRecord;
	private Integer maxRecord;

	private List<Sort> sortList;

	public Paginated() {
		this.startRecord = DEFAULT_STARTRECORD;
		this.maxRecord = DEFAULT_MAXRECORD;
		this.duePage = DEFAULT_DUEPAGE;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(Integer currentRecord) {
		this.currentRecord = currentRecord;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(Integer firstPage) {
		this.firstPage = firstPage;
	}

	public Integer getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(Integer prevPage) {
		this.prevPage = prevPage;
	}

	public Integer getPrevOnePage() {
		return prevOnePage;
	}

	public void setPrevOnePage(Integer prevOnePage) {
		this.prevOnePage = prevOnePage;
	}

	public Integer getDuePage() {
		return duePage;
	}

	public void setDuePage(Integer duePage) {
		this.duePage = duePage;
	}

	public Integer getNextOnePage() {
		return nextOnePage;
	}

	public void setNextOnePage(Integer nextOnePage) {
		this.nextOnePage = nextOnePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public void setLastPage(Integer lastPage) {
		this.lastPage = lastPage;
	}

	public Integer getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(Integer startRecord) {
		this.startRecord = startRecord;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public List<Sort> getSortList() {
		return sortList;
	}

	public void setSortList(List<Sort> sortList) {
		this.sortList = sortList;
	}

	public class Sort {
		private String property;
		private String direction;

		public Sort() {
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public String getDirection() {
			return direction;
		}

		public void setDirection(String direction) {
			this.direction = direction;
		}
	}
}
