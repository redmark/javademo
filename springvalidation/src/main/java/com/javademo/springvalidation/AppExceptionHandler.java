package com.javademo.springvalidation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * {@inheritDoc} <br>
     * set http body info from exception message
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        if (body == null)
            body = ErrorMessage.build(ex, status, (ServletWebRequest) request);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessage em = ErrorMessage.build(ex, status, (ServletWebRequest) request);
        return handleExceptionInternal(ex, em, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage em = ErrorMessage.build(ex, status, (ServletWebRequest) request);
        return handleExceptionInternal(ex, em, new HttpHeaders(), status, request);
    }
}
