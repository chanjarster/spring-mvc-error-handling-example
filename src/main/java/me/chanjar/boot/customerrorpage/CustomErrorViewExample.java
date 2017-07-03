package me.chanjar.boot.customerrorpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "me.chanjar.boot.customerrorpage", "me.chanjar.controllers" })
public class CustomErrorViewExample {

  public static void main(String[] args) {
    SpringApplication.run(CustomErrorViewExample.class, args);
  }

}
