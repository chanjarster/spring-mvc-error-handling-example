package me.chanjar.boot.customerrorattributes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by qianjia on 2017/7/1.
 */
@SpringBootApplication(scanBasePackages = { "me.chanjar.boot.customerrorattributes", "me.chanjar.controllers" })
public class CustomErrorAttributesExample {

  public static void main(String[] args) {
    SpringApplication.run(CustomErrorAttributesExample.class, args);
  }

}
