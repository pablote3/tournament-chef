package com.rossotti.tournament.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.rossotti.tournament.jpa.repository"})
public class PersistenceConfig {

	private final Environment env;

	@Autowired
	public PersistenceConfig(Environment env) {
		this.env = env;
	}

//	@Bean
//    DataSource dataSource() {
//		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//		dataSourceBuilder.driverClassName(env.getRequiredProperty("db.driver"));
//		dataSourceBuilder.url(env.getRequiredProperty("db.url"));
//		dataSourceBuilder.username(env.getRequiredProperty("db.username"));
//		dataSourceBuilder.password(env.getRequiredProperty("db.password"));
//		return dataSourceBuilder.build();
//	}
//
//	@Bean
//    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//		entityManagerFactoryBean.setDataSource(dataSource());
//		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//		entityManagerFactoryBean.setPackagesToScan("com.rossotti.basketball.jpa.model");
//
//		Properties jpaProperties = new Properties();
//		jpaProperties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
//		jpaProperties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
//		jpaProperties.put("hibernate.ejb.naming_strategy", env.getRequiredProperty("hibernate.ejb.naming_strategy"));
//		jpaProperties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
//		jpaProperties.put("hibernate.enable_lazy_load_no_trans", true);
//
//		entityManagerFactoryBean.setJpaProperties(jpaProperties);
//		return entityManagerFactoryBean;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//		return transactionManager;
//	}
}