package org.kyll.myserver.business.sysmanager.service.impl;

import org.kyll.myserver.business.sysmanager.dao.ConfigDao;
import org.kyll.myserver.business.sysmanager.entity.Config;
import org.kyll.myserver.business.sysmanager.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-12-02 16:42
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private ConfigDao configDao;

	@Override
	public Config get(Long id) {
		return configDao.get(id);
	}

	@Override
	public List<Config> getAll() {
		return configDao.getAll();
	}
}
