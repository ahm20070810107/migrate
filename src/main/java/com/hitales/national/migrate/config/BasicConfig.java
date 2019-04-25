package com.hitales.national.migrate.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(//
	entityManagerFactoryRef = "jpaEntityManagerFactory",
	transactionManagerRef = "jpaTransactionManager",
	basePackages = BasicConfig.basePackages
)
@ComponentScan(BasicConfig.basePackages)
@Data
public class BasicConfig {
    public static final String basePackages = "com.hitales";
	@Value("${hitales.national.migrate.mysql.show-sql}")
	private String showSql;

	@Value("${hitales.national.migrate.mysql.ddl-auto}")
	private String ddlAuto;

	@Bean(name = "mysqlDataSource")
	@ConfigurationProperties(prefix = "hitales.national.migrate.mysql")
	public HikariDataSource dataSource() {
		return new HikariDataSource();
	}

	@Bean(name = "mysqlTransactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "jpaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(@Qualifier("mysqlDataSource") DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPersistenceUnitName("NATIONAL-MIGRATE");
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		em.setPackagesToScan(BasicConfig.basePackages);
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", ddlAuto);
		properties.put("hibernate.show_sql", showSql);
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
		em.setJpaPropertyMap(properties);
		return em;
	}


	@Bean(name = "jpaTransactionManager")
	@Primary
	public PlatformTransactionManager jpaTransactionManager( @Qualifier("jpaEntityManagerFactory")  EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(context -> context.getSource() != null);
		modelMapper.getConfiguration().setAmbiguityIgnored(false);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

}
