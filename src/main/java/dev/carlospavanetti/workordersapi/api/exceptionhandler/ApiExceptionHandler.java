package dev.carlospavanetti.workordersapi.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {
    var problem = new Problem(status.value(), "One or more fields are invalid. Fill them correctly and try again.");

    var fields = new ArrayList<Problem.Field>();
    for (ObjectError error : ex.getBindingResult().getAllErrors()) {
      fields.add(new Problem.Field(((FieldError) error).getField(), error.getDefaultMessage()));
    }
    problem.setFields(fields);

    return super.handleExceptionInternal(ex, problem, headers, status, request);
  }

}
