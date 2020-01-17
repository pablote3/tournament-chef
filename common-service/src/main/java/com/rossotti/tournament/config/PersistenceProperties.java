package com.rossotti.tournament.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("datasource")
public class PersistenceProperties {

	private final App app = new App();

	public App getApp() {
		return app;
	}

	public static class App {
		private String driver;
		private String url;

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
	}
}