package pfq.app.security.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("pfq.app.security.*")
public class DbConfig implements WebMvcConfigurer {

	@Bean(name = "dataSource")
	public DataSource dataSource(@Value("${datasource.driver}") String dataSourceDriver,
			@Value("${datasource.url}") String dataSourceUrl, @Value("${datasource.username}") String dataSourceUser,
			@Value("${datasource.password}") String dataSourcePassword,
			@Value("${datasource.schema}") String dataSourceScheme) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(dataSourceDriver);
		dataSource.setUrl(dataSourceUrl);
		dataSource.setUsername(dataSourceUser);
		dataSource.setPassword(dataSourcePassword);
		return dataSource;
	}
	
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			@Qualifier("dataSource") DataSource dataSource, @Value("${hibernate.max_fetch_depth}") String maxFetchDepth,
			@Value("${hibernate.jdbc.fetch_size}") String fetchSize,
			@Value("${hibernate.jdbc.batch_size}") String batchSize,
			@Value("${hibernate.show_sql}") String showSql,
			@Value("${hibernate.hb2ddl.auto}") String hb2ddl

	) {
		final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		factory.setPackagesToScan("pfq.app.security.model");
		final Properties properties = new Properties();
		properties.put("hibernate.max_fetch_depth", maxFetchDepth);
		properties.put("hibernate.jdbc.fetch_size", fetchSize);
		properties.put("hibernate.jdbc.batch_size", batchSize);
		properties.put("hibernate.show_sql", showSql);
		properties.put("hibernate.hbm2ddl.auto", hb2ddl);
		factory.setJpaProperties(properties);
		return factory;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(
			@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return transactionManager;
	}
}
