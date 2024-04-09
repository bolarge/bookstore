package com.demo.bookstore.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@ControllerAdvice
public class BookStoreControllerAdvice extends ResponseEntityExceptionHandler  {

	private final MessageSource messageSource;

	@ResponseBody
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe) {
		ProblemDetail errorDetail = new ProblemDetail("Resource not found", HttpStatus.NOT_FOUND.value(),
      "Resource Not Found", Long.parseLong(LocalDateTime.now().toString()), rnfe.getMessage(), null);
		return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException manve, HttpHeaders headers, HttpStatus status, WebRequest request) {

    ProblemDetail errorDetail = new ProblemDetail("Validation Failed", HttpStatus.BAD_REQUEST.value(), "Input validation failed",
      Long.parseLong(LocalDateTime.now().toString()), manve.getMessage(), null);

		List<FieldError> fieldErrors =  manve.getBindingResult().getFieldErrors();
		for(FieldError fe : fieldErrors) {
      List<ValidationError> validationErrorList = errorDetail.validationErrors().computeIfAbsent(fe.getField(), k -> new ArrayList<>());
      ValidationError validationError = new ValidationError(fe.getCode(),messageSource.getMessage(fe, Locale.getDefault()));
			validationErrorList.add(validationError);
		}
		return handleExceptionInternal(manve, errorDetail, headers, status, request);
	}

	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
    ProblemDetail errorDetail = new ProblemDetail("Message not readable", HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
      Long.parseLong(LocalDateTime.now().toString()), ex.getClass().getName(), null);
		return handleExceptionInternal(ex, errorDetail, headers, status, request);
	}
}
