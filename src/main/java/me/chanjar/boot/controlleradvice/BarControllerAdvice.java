package me.chanjar.boot.controlleradvice;

import me.chanjar.exception.AnotherException;
import me.chanjar.exception.SomeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(assignableTypes = BarController.class)
public class BarControllerAdvice {

  @ExceptionHandler(SomeException.class)
  String handleSomeException(HttpServletRequest request, Throwable ex) {
    return "/controlleradvice/some-ex-error";

  }

  @ExceptionHandler(AnotherException.class)
  @ResponseBody
  ResponseEntity<?> handleAnotherException(HttpServletRequest request, Throwable ex) {
    HttpStatus status = getStatus(request);
    return new ResponseEntity<>(new AnotherExceptionErrorMessage(status.value(), ex.getMessage()), status);
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE);
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }

}
