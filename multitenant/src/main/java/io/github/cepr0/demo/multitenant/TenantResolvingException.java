package io.github.cepr0.demo.multitenant;

public class TenantResolvingException extends Exception {
	public TenantResolvingException(Throwable throwable, String message) {
		super(message, throwable);
	}
}
