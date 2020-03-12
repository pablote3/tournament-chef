package com.rossotti.tournament.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = "com.rossotti.tournament")
public class ServiceConfig {

	@Autowired
	public ServiceConfig(Environment env) {
	}
}