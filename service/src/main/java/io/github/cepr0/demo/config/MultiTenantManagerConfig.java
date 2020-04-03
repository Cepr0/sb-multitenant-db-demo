package io.github.cepr0.demo.config;

import io.github.cepr0.demo.multitenant.MultiTenantManager;
import io.github.cepr0.demo.tenant.TenantRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MultiTenantManagerConfig {

    private final TenantRepo tenantRepo;

    public MultiTenantManagerConfig(MultiTenantManager tenantManager, TenantRepo tenantRepo) {
        tenantManager.setTenantResolver(this::tenantResolver);
        this.tenantRepo = tenantRepo;
    }

    private DataSourceProperties tenantResolver(String tenantId) {
        return tenantRepo.findById(tenantId).map(tenant -> {
            DataSourceProperties properties = new DataSourceProperties();
            properties.setUrl(tenant.getUrl());
            properties.setUsername(tenant.getUsername());
            properties.setPassword(tenant.getPassword());
            return properties;
        }).orElseThrow(() -> {
            String msg = "Tenant properties not found for " + tenantId;
            log.error("[!] " + msg);
            return new RuntimeException(msg);
        });
    }
}
