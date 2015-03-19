package org.kyll.idea.busi.metdl.dao;

import org.kyll.idea.busi.metdl.model.Met;
import org.kyll.idea.busi.metdl.model.MetImage;
import org.kyll.idea.busi.metdl.model.MetUrl;

import java.util.List;
import java.util.Map;

public interface MetDao {
	Integer selectMetCount(Met met);

	List<Met> selectMetList(Map<String, Object> paramMap);

	Met selectMetById(String id);

	Met selectMetByName(String name);

	MetImage selectMetImageById(String id);

	String insertMet(Met met);

	String insertUrl(MetUrl metUrl);

	String insertMetImage(MetImage metImage);

	void updateMetById(Met met);

	void updateMetUnfinishedStatus();

	void updateMetCategoryByOldCategorys(Map<String, Object> paramMap);
}
