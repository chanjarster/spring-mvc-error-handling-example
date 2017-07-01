package me.chanjar.controllers;

import me.chanjar.exception.SomeException;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by qianjia on 2017/7/1.
 */
@RestController
public class FooRestController {

  @RequestMapping(value = "/return-json-2", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
  @ResponseBody
  public FooRestController returnJson2() throws SomeException {
    throw new SomeException();
  }

}
