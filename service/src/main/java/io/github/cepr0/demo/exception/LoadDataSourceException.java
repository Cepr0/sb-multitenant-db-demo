package io.github.cepr0.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LoadDataSourceException extends RuntimeException {
	public LoadDataSourceException(Throwable cause) {
		super("Could not load DataSource! - " + cause.getMessage());
	}
}
