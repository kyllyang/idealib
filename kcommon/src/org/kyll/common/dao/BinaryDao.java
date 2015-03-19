package org.kyll.common.dao;

import org.kyll.common.model.Binary;

public interface BinaryDao {
	Binary selectBinaryById(String id);

	String insertBinary(Binary binary);

	int updateBinaryById(Binary binary);

	int deleteBinaryById(String id);
}
