package io.github.cepr0.demo.controller;

import io.github.cepr0.demo.service.TenantService;
import io.github.cepr0.demo.tenant.Tenant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tenants")
public class TenantController {
	
	private final TenantService tenantService;
	
	public TenantController(TenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * Get list of all tenant Ids
	 */
	@GetMapping
	public List<String> getList() {
		log.info("[i] Received 'Get tenant list' request");
		return tenantService.getList();
	}

	/**
	 * Add the new tenant on the fly
	 */
	@PostMapping
	public Tenant add(@RequestBody Tenant tenant) {
		log.info("[i] Received 'Add new tenant' request {}", tenant);
		return tenantService.add(tenant);
	}
}
