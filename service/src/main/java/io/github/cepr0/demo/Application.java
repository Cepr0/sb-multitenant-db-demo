package io.github.cepr0.demo;

import io.github.cepr0.demo.multitenant.MultiTenantManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Properties;

import static java.lang.String.format;

@Slf4j
@EntityScan("io.github.cepr0.demo.model")
@SpringBootApplication
public class Application {
	
	private final MultiTenantManager tenantManager;
	
	public Application(MultiTenantManager tenantManager) {
		this.tenantManager = tenantManager;
		this.tenantManager.setTenantResolver(Application::tenantResolver);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@SneakyThrows(IOException.class)
	@EventListener
	public void onReady(ApplicationReadyEvent event) {

		File[] files = Paths.get("tenants/onStartUp").toFile().listFiles();

		if (files == null) {
			log.warn("[!] Tenant property files not found at ./tenants/onStartUp folder!");
			return;
		}

		for (File propertyFile : files) {
			Properties tenantProperties = new Properties();
			tenantProperties.load(new FileInputStream(propertyFile));

			String tenantId = tenantProperties.getProperty("id");
			String url = tenantProperties.getProperty("url");
			String username = tenantProperties.getProperty("username");
			String password = tenantProperties.getProperty("password");

			try {
				tenantManager.addTenant(tenantId, url, username, password);
				log.info("[i] Loaded DataSource for tenant '{}'.", tenantId);
			} catch (SQLException e) {
				log.error(format("[!] Could not load DataSource for tenant '%s'!", tenantId), e);
			}
		}
	}

	private static DataSourceProperties tenantResolver(String tenantId) {

		File[] files = Paths.get("tenants/atRuntime").toFile().listFiles();

		if (files == null) {
			String msg = "[!] Tenant property files not found at ./tenants/atRuntime folder!";
			log.error(msg);
			throw new RuntimeException(msg);
		}

		for (File propertyFile : files) {
			Properties tenantProperties = new Properties();
			try {
				tenantProperties.load(new FileInputStream(propertyFile));
			} catch (IOException e) {
				String msg = "[!] Could not read tenant property file at ./tenants/atRuntime folder!";
				log.error(msg);
				throw new RuntimeException(msg, e);
			}

			String id = tenantProperties.getProperty("id");
			if (tenantId.equals(id)) {
				DataSourceProperties properties = new DataSourceProperties();
				properties.setUrl(tenantProperties.getProperty("url"));
				properties.setUsername(tenantProperties.getProperty("username"));
				properties.setPassword(tenantProperties.getProperty("password"));
				return properties;
			}
		}
		String msg = "[!] Any tenant property files not found at ./tenants/atRuntime folder!";
		log.error(msg);
		throw new RuntimeException(msg);
	}
}
