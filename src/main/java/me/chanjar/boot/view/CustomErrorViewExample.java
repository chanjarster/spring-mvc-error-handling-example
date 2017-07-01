package me.chanjar.boot.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by qianjia on 2017/7/1.
 */
@SpringBootApplication(scanBasePackages = { "me.chanjar.boot.view", "me.chanjar.boot.def" })
public class CustomErrorViewExample {

  public static void main(String[] args) {
    SpringApplication.run(CustomErrorViewExample.class, args);
  }

}
