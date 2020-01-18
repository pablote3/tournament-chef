package com.rossotti.tournament.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@ConstructorBinding
@ConfigurationProperties("spring")

public class YAMLConfig {

//	public App app() {
//		return new App();
//	}
//
//	public static class App {
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
//	}
}