package me.chanjar.boot.def;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "me.chanjar.controllers")
public class DefaultExample {

  public static void main(String[] args) {
    SpringApplication.run(DefaultExample.class, args);
  }

}
