package me.chanjar.boot.customerrorattributes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "me.chanjar.boot.customerrorattributes", "me.chanjar.controllers" })
public class CustomErrorAttributesExample {

  public static void main(String[] args) {
    SpringApplication.run(CustomErrorAttributesExample.class, args);
  }

}
