package me.chanjar.boot.customdefaulterrorview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "me.chanjar.boot.customdefaulterrorview", "me.chanjar.controllers" })
public class CustomDefaultErrorViewExample {

  public static void main(String[] args) {
    SpringApplication.run(CustomDefaultErrorViewExample.class, args);
  }

}
