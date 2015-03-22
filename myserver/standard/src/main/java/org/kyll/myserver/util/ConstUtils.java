package org.kyll.myserver.util;

import org.kyll.myserver.business.sysmanager.entity.Config;
import org.kyll.myserver.business.sysmanager.service.ConfigService;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Kyll
 * Date: 2014-12-02 16:37
 */
public class ConstUtils {
	public static ApplicationContext applicationContext;
	private static Map<String, String> configMap = new HashMap<>();

	public static String getConfig(String key) {
		if (configMap.size() == 0) {
			ConfigService configService = applicationContext.getBean(ConfigService.class);
			List<Config> configList = configService.getAll();
			for (Config config : configList) {
				configMap.put(config.getKey(), config.getValue());
			}
		}

		return configMap.get(key);
	}

	public static String getAttachmentPath() {
		return getConfig("ATTACHMENT_PATH");
	}
}
