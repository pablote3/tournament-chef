package com.rossotti.tournament.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(YAMLConfig.class)
@EnableJpaRepositories(basePackages = {"com.rossotti.tournament.jpa.repository"})
public class PersistenceConfig {

	private final YAMLConfig config;

	@Autowired
	public PersistenceConfig(YAMLConfig config) {
		this.config = config;
	}

	@Bean
    DataSource dataSource() {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(config.getDb().getDriver());
		dataSourceBuilder.url(config.getDb().getUrl());
		dataSourceBuilder.username(config.getDb().getUsername());
		dataSourceBuilder.password(config.getDb().getPassword());
		return dataSourceBuilder.build();
	}

	@Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.rossotti.tournament.model");

		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect", config.getHibernate().getDialect());
		jpaProperties.put("hibernate.hbm2ddl.auto", config.getHibernate().getHbm2ddlAuto());
		jpaProperties.put("hibernate.ejb.naming_strategy", "org.hibernate.cfg.ImprovedNamingStrategy");
		jpaProperties.put("hibernate.show_sql", config.getHibernate().getShowSql());
		jpaProperties.put("hibernate.enable_lazy_load_no_trans", true);

		entityManagerFactoryBean.setJpaProperties(jpaProperties);
		return entityManagerFactoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
}