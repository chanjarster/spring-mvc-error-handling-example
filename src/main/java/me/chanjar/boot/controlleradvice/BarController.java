package me.chanjar.boot.controlleradvice;

import me.chanjar.exception.AnotherException;
import me.chanjar.exception.SomeException;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bar")
public class BarController {

  @RequestMapping("/html-a")
  public String getHtmlA() throws SomeException {
    throw new SomeException();
  }

  @RequestMapping("/html-b")
  public String getHtmlB() throws AnotherException {
    throw new AnotherException();
  }

  @RequestMapping(value = "/json-a", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String getJsonA() throws SomeException {
    throw new SomeException();
  }

  @RequestMapping(value = "/json-b", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @ResponseBody
  public String getJsonB() throws AnotherException {
    throw new AnotherException();
  }

  @RequestMapping(value = "/text-plain-a", produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
  @ResponseBody
  public String getTextPlainA() throws SomeException {
    throw new SomeException();
  }

  @RequestMapping(value = "/text-plain-b", produces = MimeTypeUtils.TEXT_PLAIN_VALUE)
  @ResponseBody
  public String getTextPlainB() throws AnotherException {
    throw new AnotherException();
  }

}
