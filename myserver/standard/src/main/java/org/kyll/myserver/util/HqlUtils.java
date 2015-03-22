package org.kyll.myserver.util;

import org.kyll.myserver.base.paginated.Paginated;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-11-08 11:40
 */
public class HqlUtils {
	public static void appendOrderBy(StringBuilder hql, String alais, Paginated pg) {
		List<Paginated.Sort> sortList = pg.getSortList();
		if (sortList != null && !sortList.isEmpty()) {
			hql.append(" order by");
			for (Paginated.Sort sort : sortList) {
				hql.append(" ").append(alais).append(".").append(sort.getProperty()).append(" ").append(sort.getDirection());
			}
		}
	}

	private HqlUtils() {
	}
}
