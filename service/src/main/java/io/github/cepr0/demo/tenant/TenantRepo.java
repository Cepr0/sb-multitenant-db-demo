package io.github.cepr0.demo.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepo extends JpaRepository<Tenant, String> {
}
