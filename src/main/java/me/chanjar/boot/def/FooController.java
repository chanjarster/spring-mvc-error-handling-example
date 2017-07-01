package me.chanjar.boot.def;

import me.chanjar.exception.SomeException;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

/**
 * Created by qianjia on 2017/7/1.
 */
@Controller
public class FooController {

  @RequestMapping("/return-model-and-view")
  public ModelAndView returnModelAndView() throws SomeException {
    throw new SomeException();
  }

  @RequestMapping("/return-view-name")
  public String returnViewName() throws SomeException {
    throw new SomeException();
  }

  @RequestMapping("/return-view")
  public View returnView() throws SomeException {
    throw new SomeException();
  }

  @RequestMapping("/return-plain-text")
  @ResponseBody
  public String returnPlainText() throws SomeException {
    throw new SomeException();
  }

  @RequestMapping(value = "/return-json-1", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String returnJson1() throws SomeException {
    throw new SomeException();
  }

}
