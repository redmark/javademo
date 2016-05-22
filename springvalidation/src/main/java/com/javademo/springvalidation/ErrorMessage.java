package com.javademo.springvalidation;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * Created by dyliz on 2016/4/7.
 */
public class ErrorMessage {
    private long timestamp;
    private int status;
    private String error;
    private String exception;
    private List<Map<String, String>> errors;
    private String message;
    private String path;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private void addBeanError(String field, String message) {
        if (errors == null)
            errors = new ArrayList<>();
        Map<String, String> e = new HashMap<>();
        e.put("field", field);
        e.put("message", message);
        errors.add(e);
    }

    private void addValueError(String value, String message) {
        if (errors == null)
            errors = new ArrayList<>();
        Map<String, String> e = new HashMap<>();
        e.put("value", value);
        e.put("message", message);
        errors.add(e);
    }

    public static ErrorMessage build(Exception ex, HttpStatus status, ServletWebRequest request) {
        ErrorMessage errMsg = new ErrorMessage();
        errMsg.setupBase(ex, status, request);
        errMsg.setupExtra(ex);
        return errMsg;
    }

    private void setupBase(Exception ex, HttpStatus status, ServletWebRequest request) {
        this.setTimestamp(System.currentTimeMillis());
        this.setStatus(status.value());
        this.setError(status.getReasonPhrase());
        this.setException(ex.getClass().getSimpleName());
        this.setMessage(ex.getMessage());
        this.setPath(request.getRequest().getRequestURI());
    }

    private void setupExtra(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException)
            setupExtra((MethodArgumentNotValidException) ex);
        else if (ex instanceof ConstraintViolationException)
            setupExtra((ConstraintViolationException) ex);
        else if (ex instanceof HttpMessageNotReadableException)
            setupExtra((HttpMessageNotReadableException) ex);
        else {
            //do nothing
        }
    }

    private void setupExtra(MethodArgumentNotValidException ex) {
        List<ObjectError> errs = ex.getBindingResult().getAllErrors();
        errs.forEach(e -> this.addBeanError(((FieldError) e).getField(), e.getDefaultMessage()));
        this.setMessage("validation failed");
    }

    private void setupExtra(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> errs = ex.getConstraintViolations();
        errs.forEach(e -> this.addValueError(e.getInvalidValue().toString(), e.getMessage()));
        this.setMessage("validation failed");
    }

    private void setupExtra(HttpMessageNotReadableException ex) {
        this.setMessage("illegal json format");
    }
}
