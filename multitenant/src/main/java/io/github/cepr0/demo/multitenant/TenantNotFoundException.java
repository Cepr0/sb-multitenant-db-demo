package io.github.cepr0.demo.multitenant;

public class TenantNotFoundException extends Exception {
	public TenantNotFoundException(String message) {
		super(message);
	}
}
