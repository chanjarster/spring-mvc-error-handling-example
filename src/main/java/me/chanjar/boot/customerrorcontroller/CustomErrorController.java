package me.chanjar.boot.customerrorcontroller;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 模仿 {@link BasicErrorController}的配置
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {

  public CustomErrorController(ErrorAttributes errorAttributes,
      ErrorProperties errorProperties) {
    super(errorAttributes, errorProperties);
  }

  public CustomErrorController(ErrorAttributes errorAttributes,
      ErrorProperties errorProperties,
      List<ErrorViewResolver> errorViewResolvers) {
    super(errorAttributes, errorProperties, errorViewResolvers);
  }

  @RequestMapping(produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
  @ResponseBody
  public String errorTextPlan(HttpServletRequest request) {

    Map<String, Object> body = getErrorAttributes(request,
        isIncludeStackTrace(request, MediaType.ALL));
    body.put("status", getStatus(request));
    return body.toString();

  }

}
