package me.chanjar.boot.def;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by qianjia on 2017/7/1.
 */
@SpringBootApplication(scanBasePackages = "me.chanjar.controllers")
public class DefaultExample {

  public static void main(String[] args) {
    SpringApplication.run(DefaultExample.class, args);
  }

}
