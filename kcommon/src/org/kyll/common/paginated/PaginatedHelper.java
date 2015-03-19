package org.kyll.common.paginated;

import java.util.ArrayList;
import java.util.List;

public final class PaginatedHelper {
	public static Paginated defaultPaginated() {
		return makeDateset(new Paginated(), 0, new ArrayList<>()).getPaginated();
	}

	public static <T> Dataset<T> makeDateset(Paginated paginated, Integer totalRecord, List<T> dataList) {
		paginated.setTotalRecord(totalRecord);
		paginated.setTotalPage(totalRecord % paginated.getMaxRecord() == 0 ? totalRecord / paginated.getMaxRecord() : totalRecord / paginated.getMaxRecord() + 1);
	//	paginated.setCurrentRecord();
		paginated.setCurrentPage(paginated.getStartRecord() / paginated.getMaxRecord() + 1);
		paginated.setFirstPage(0);
		paginated.setLastPage((paginated.getTotalPage() - 1) * paginated.getMaxRecord());
		paginated.setPrevPage(paginated.getStartRecord() == 0 ? 0 : (paginated.getStartRecord() - (paginated.getMaxRecord() * paginated.getDuePage()) > 0 ? paginated.getStartRecord() - (paginated.getMaxRecord() * paginated.getDuePage()) : 0));
		paginated.setNextPage(paginated.getStartRecord() + (paginated.getMaxRecord() * paginated.getDuePage()) < paginated.getTotalRecord() ? paginated.getStartRecord() + (paginated.getMaxRecord() * paginated.getDuePage()): paginated.getLastPage());
		paginated.setPrevOnePage(paginated.getStartRecord() == 0 ? 0 : paginated.getStartRecord() - paginated.getMaxRecord());
		paginated.setNextOnePage(paginated.getStartRecord() + paginated.getMaxRecord() < paginated.getTotalRecord() ? paginated.getStartRecord() + paginated.getMaxRecord() : paginated.getLastPage());

		Dataset<T> dataset = new Dataset<T>();
		dataset.setPaginated(paginated);
		dataset.setDataList(dataList);
		return dataset;
	}

	public static String makeParam(Paginated paginated) {
		return "paginated.startRecord=" + paginated.getStartRecord() + "&paginated.maxRecord=" + paginated.getMaxRecord() + "&paginated.duePage=" + paginated.getDuePage();
	}

	private PaginatedHelper() {
	}
}
