package io.github.cepr0.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The given Database parameters are incorrect!")
public class InvalidDbPropertiesException extends RuntimeException {
}
