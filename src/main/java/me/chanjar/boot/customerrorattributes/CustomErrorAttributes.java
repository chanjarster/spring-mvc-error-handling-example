package me.chanjar.boot.customerrorattributes;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;

import java.util.HashMap;
import java.util.Map;

public class CustomErrorAttributes implements ErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
    Map<String, Object> result = new HashMap<>();
    result.put("timestamp", System.currentTimeMillis());
    result.put("error", "customized error");
    result.put("path", "customized path");
    result.put("status", 100);
    result.put("exception", "customized exception");
    result.put("message", "customized message");
    result.put("trace", "customized trace");
    result.put("add-attribute", "add-attribute");
    return result;
  }

  @Override
  public Throwable getError(RequestAttributes requestAttributes) {
    return null;
  }

}
