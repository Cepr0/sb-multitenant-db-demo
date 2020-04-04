package io.github.cepr0.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(
        basePackages = "io.github.cepr0.demo.repo",
        entityManagerFactoryRef = "tenantEntityManager",
        transactionManagerRef = "tenantTransactionManager"
)
public class TenantDataSourceConfig extends AbstractDataSourceConfig {

    @Primary
    @Bean("tenantEntityManager")
    public LocalContainerEntityManagerFactoryBean tenantEntityManager(DataSource tenantDataSource) {
        return entityManager(tenantDataSource, "io.github.cepr0.demo.model", "none");
    }

    @Primary
    @Bean
    public PlatformTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManager") LocalContainerEntityManagerFactoryBean tenantEntityManager) {
        return transactionManager(tenantEntityManager);
    }
}
