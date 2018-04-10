package io.github.cepr0.demo.controller;

import io.github.cepr0.demo.exception.InvalidDbPropertiesException;
import io.github.cepr0.demo.exception.InvalidTenantIdExeption;
import io.github.cepr0.demo.model.Model;
import io.github.cepr0.demo.multitenant.MultiTenantManager;
import io.github.cepr0.demo.multitenant.TenantNotFoundException;
import io.github.cepr0.demo.multitenant.TenantResolvingException;
import io.github.cepr0.demo.service.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Slf4j
@RestController
@RequestMapping("/models")
public class ModelController {
	
	private static final String MSG_INVALID_TENANT_ID = "[!] DataSource not found for given tenant Id '{}'!";
	private static final String MSG_INVALID_DB_PROPERTIES_ID = "[!] DataSource properties related to the given tenant ('{}') is invalid!";
	private static final String MSG_RESOLVING_TENANT_ID = "[!] Could not resolve tenant ID '{}'!";

	private final ModelService modelService;
	private final MultiTenantManager tenantManager;
	
	public ModelController(ModelService modelService, MultiTenantManager tenantManager) {
		this.modelService = modelService;
		this.tenantManager = tenantManager;
	}
	
	@GetMapping
	public ResponseEntity<?> getAll(@RequestHeader(value = "X-TenantID") String tenantId) {
		setTenant(tenantId);
		return ResponseEntity.ok(modelService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<?> create(@RequestHeader("X-TenantId") String tenantId) {
		log.info("[i] Received POST request for '{}'", tenantId);
		setTenant(tenantId);
		return ResponseEntity.ok(modelService.save(new Model(tenantId)));
	}

	private void setTenant(String tenantId) {
		try {
			tenantManager.setCurrentTenant(tenantId);
		} catch (SQLException e) {
			log.error(MSG_INVALID_DB_PROPERTIES_ID, tenantId);
			throw new InvalidDbPropertiesException();
		} catch (TenantNotFoundException e) {
			log.error(MSG_INVALID_TENANT_ID, tenantId);
			throw new InvalidTenantIdExeption();
		} catch (TenantResolvingException e) {
			log.error(MSG_RESOLVING_TENANT_ID, tenantId);
			throw new InvalidTenantIdExeption();
		}
	}
}
