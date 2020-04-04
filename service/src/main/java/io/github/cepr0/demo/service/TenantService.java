package io.github.cepr0.demo.service;

import io.github.cepr0.demo.exception.LoadDataSourceException;
import io.github.cepr0.demo.multitenant.MultiTenantManager;
import io.github.cepr0.demo.tenant.Tenant;
import io.github.cepr0.demo.tenant.TenantRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional(transactionManager = "adminTransactionManager")
public class TenantService {

    private final TenantRepo tenantRepo;
    private final MultiTenantManager tenantManager;

    public TenantService(TenantRepo tenantRepo, MultiTenantManager tenantManager) {
        this.tenantRepo = tenantRepo;
        this.tenantManager = tenantManager;
    }

    @Transactional(readOnly = true)
    public List<String> getList() {
        return tenantRepo.findAll().stream().map(Tenant::getId).collect(toList());
    }

    public Tenant add(Tenant tenant) {
        try {
            tenantRepo.save(tenant);
            tenantManager.addTenant(tenant.getId(), tenant.getUrl(), tenant.getUsername(), tenant.getPassword());
            log.info("[i] Loaded DataSource for tenant '{}'.", tenant.getId());
            return tenant;
        } catch (SQLException e) {
            throw new LoadDataSourceException(e);
        }
    }
}
