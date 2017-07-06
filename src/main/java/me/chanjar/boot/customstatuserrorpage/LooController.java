package me.chanjar.boot.customstatuserrorpage;

import me.chanjar.exception.AnotherException;
import me.chanjar.exception.SomeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/loo")
public class LooController {

  @Autowired
  private ErrorAttributes errorAttributes;

  @RequestMapping("/error-403")
  public String error403() {
    throw new Exception403();
  }

  @RequestMapping("/error-406")
  public String error406() {
    throw new Exception406();
  }

  @RequestMapping("/error-600")
  public String error600(HttpServletRequest request, HttpServletResponse response) throws SomeException {
    request.setAttribute(WebUtils.ERROR_STATUS_CODE_ATTRIBUTE, 600);
    response.setStatus(600);
    throw new SomeException();
  }

  @RequestMapping("/error-601")
  public String error601(HttpServletRequest request, HttpServletResponse response) throws AnotherException {
    throw new AnotherException();
  }

  @ExceptionHandler(AnotherException.class)
  String handleAnotherException(HttpServletRequest request, HttpServletResponse response, Model model)
      throws IOException {
    // 需要设置Status Code，否则响应结果会是200
    response.setStatus(601);
    model.addAllAttributes(errorAttributes.getErrorAttributes(new ServletRequestAttributes(request), true));
    return "error/6xx";
  }

}
