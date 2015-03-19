package org.kyll.common.dao.ibatis;

import org.kyll.common.base.dao.IbatisDao;
import org.kyll.common.dao.BinaryDao;
import org.kyll.common.model.Binary;
import org.springframework.stereotype.Repository;

@Repository
public class BinaryDaoImpl extends IbatisDao implements BinaryDao {
	@Override
	public Binary selectBinaryById(String id) {
		return (Binary) getSqlMapClientTemplate().queryForObject("Binary.selectBinaryById", id);
	}

	@Override
	public String insertBinary(Binary binary) {
		return (String) getSqlMapClientTemplate().insert("Binary.insertBinary", binary);
	}

	@Override
	public int updateBinaryById(Binary binary) {
		return getSqlMapClientTemplate().update("Binary.updateBinaryById", binary);
	}

	@Override
	public int deleteBinaryById(String id) {
		return getSqlMapClientTemplate().delete("Binary.deleteBinaryById", id);
	}
}
