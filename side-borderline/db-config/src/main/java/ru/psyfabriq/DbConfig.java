package ru.psyfabriq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories("ru.psyfabriq")
public class DbConfig implements WebMvcConfigurer {
    Logger logger = LoggerFactory.getLogger(DbConfig.class);

    @Bean(name = "dataSource")
    public DataSource dataSource(@Value("${datasource.driver}") String dataSourceDriver,
                                 @Value("${datasource.url}") String dataSourceUrl, @Value("${datasource.username}") String dataSourceUser,
                                 @Value("${datasource.password}") String dataSourcePassword,
                                 @Value("${datasource.schema}") String dataSourceScheme) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        logger.warn(String.format("%s %s", "dataSourceUrl", dataSourceUrl));
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
        // Создание класса фабрики, реализующей интерфейс
        // FactoryBean<EntityManagerFactory>
        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        // Задание источника подключения
        factory.setDataSource(dataSource);
        // Задание адаптера для конкретной реализации JPA
        // указывает, какая именно библиотека будет использоваться в качестве поставщика
        // постоянства
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        // Указание пакетов, в которых будут находиться классы-сущности
        factory.setPackagesToScan("ru.psyfabriq.entity");
        // factory.setPersistenceUnitName("persistenceUnit");
        // Создание свойств для настройки Hibernate
        final Properties properties = new Properties();
        // Указание максимальной глубины связи (будет рассмотрено в следующем уроке)
        properties.put("hibernate.max_fetch_depth", maxFetchDepth);
        // Определение максимального количества строк, возвращаемых за один запрос из БД
        properties.put("hibernate.jdbc.fetch_size", fetchSize);
        // Определение максимального количества запросов при использовании пакетных
        // операций
        properties.put("hibernate.jdbc.batch_size", batchSize);
        // Включает логирование
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
