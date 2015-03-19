package org.kyll.common.facade.impl;

import org.kyll.common.dao.BinaryDao;
import org.kyll.common.model.Binary;
import org.kyll.common.facade.BinaryFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BinaryFacadeImpl implements BinaryFacade {
	@Autowired
	private BinaryDao binaryDao;

	@Override
	public Binary getBinaryById(Binary binary) {
		return binaryDao.selectBinaryById(binary.getId());
	}
}
