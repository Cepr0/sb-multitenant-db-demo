package io.github.cepr0.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@PropertySource({ "classpath:application.yml" })
@EnableJpaRepositories(
        basePackages = "io.github.cepr0.demo.tenant",
        entityManagerFactoryRef = "adminEntityManager",
        transactionManagerRef = "adminTransactionManager"
)
public class AdminDataSourceConfig extends AbstractDataSourceConfig {

    private final Environment env;

    public AdminDataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    DataSource adminDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driverClassName")));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean adminEntityManager() {
        return entityManager(adminDataSource(), "io.github.cepr0.demo.tenant", "validate");
    }

    @Bean
    public PlatformTransactionManager adminTransactionManager() {
        return transactionManager(adminEntityManager());
    }
}
