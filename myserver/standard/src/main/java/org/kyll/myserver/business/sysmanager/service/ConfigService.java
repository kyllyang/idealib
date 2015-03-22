package org.kyll.myserver.business.sysmanager.service;

import org.kyll.myserver.base.BaseService;
import org.kyll.myserver.business.sysmanager.entity.Config;

import java.util.List;

/**
 * User: Kyll
 * Date: 2014-12-02 16:41
 */
public interface ConfigService extends BaseService<Config, Long> {
	List<Config> getAll();
}
