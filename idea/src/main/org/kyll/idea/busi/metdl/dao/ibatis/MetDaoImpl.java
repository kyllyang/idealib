package org.kyll.idea.busi.metdl.dao.ibatis;

import org.kyll.common.base.dao.IbatisDao;
import org.kyll.idea.busi.metdl.dao.MetDao;
import org.kyll.idea.busi.metdl.model.Met;
import org.kyll.idea.busi.metdl.model.MetImage;
import org.kyll.idea.busi.metdl.model.MetUrl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MetDaoImpl extends IbatisDao implements MetDao {
	@Override
	public Integer selectMetCount(Met met) {
		return (Integer) sqlMapClientTemplate.queryForObject("Met.selectMetCount", met);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Met> selectMetList(Map<String, Object> paramMap) {
		return sqlMapClientTemplate.queryForList("Met.selectMetList", paramMap);
	}

	@Override
	public Met selectMetById(String id) {
		return (Met) sqlMapClientTemplate.queryForObject("Met.selectMetById", id);
	}

	@Override
	public Met selectMetByName(String name) {
		return (Met) sqlMapClientTemplate.queryForObject("Met.selectMetByName", name);
	}

	@Override
	public MetImage selectMetImageById(String id) {
		return (MetImage) sqlMapClientTemplate.queryForObject("Met.selectImageById", id);
	}

	@Override
	public String insertMet(Met met) {
		return (String) sqlMapClientTemplate.insert("Met.insertMet", met);
	}

	@Override
	public String insertUrl(MetUrl metUrl) {
		return (String) sqlMapClientTemplate.insert("Met.insertUrl", metUrl);
	}

	@Override
	public String insertMetImage(MetImage metImage) {
		return (String) sqlMapClientTemplate.insert("Met.insertImage", metImage);
	}

	@Override
	public void updateMetById(Met met) {
		sqlMapClientTemplate.update("Met.updateMetById", met);
	}

	@Override
	public void updateMetUnfinishedStatus() {
		sqlMapClientTemplate.update("Met.updateMetUnfinishedStatus");
	}

	@Override
	public void updateMetCategoryByOldCategorys(Map<String, Object> paramMap) {
		sqlMapClientTemplate.update("Met.updateMetCategoryByOldCategorys", paramMap);
	}
}
