package net.cis.utils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
 * @author Liemnh
 *
 */
public class CustomProperties extends Properties {
	protected final Logger LOGGER = Logger.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	public CustomProperties(DataSource dataSource) {
		super();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> configs = jdbcTemplate
				.queryForList("SELECT config_key, config_value FROM parking_config");
		for (Map<String, Object> config : configs) {
			setProperty((config.get("config_key")).toString(), (config.get("config_value")).toString());
		}
	}
}