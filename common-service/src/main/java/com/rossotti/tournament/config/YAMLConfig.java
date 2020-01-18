package com.rossotti.tournament.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConstructorBinding
@ConfigurationProperties("spring")

public class YAMLConfig {

	private final Db db = new Db();
	private final Hibernate hibernate = new Hibernate();

	public Db getDb() {
		return db;
	}
	public Hibernate getHibernate() {
		return hibernate;
	}

	public static class Db {
		private String driver;
		private String url;
		private String username;
		private String password;

		public String getDriver() {
			return driver;
		}
		public void setDriver(String driver) {
			this.driver = driver;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}

	public static class Hibernate {
		private String dialect;
		private String hbm2ddlAuto;
		private String showSql;

		public String getDialect() {
			return dialect;
		}
		public void setDialect(String dialect) {
			this.dialect = dialect;
		}
		public String getHbm2ddlAuto() {
			return hbm2ddlAuto;
		}
		public void setHbm2ddlAuto(String hbm2ddlAuto) {
			this.hbm2ddlAuto = hbm2ddlAuto;
		}
		public String getShowSql() {
			return showSql;
		}
		public void setShowSql(String showSql) {
			this.showSql = showSql;
		}
	}
}