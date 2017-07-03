package me.chanjar.boot.customerrorattributes;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomErrorAttributesConfiguration {

  @Bean
  public ErrorAttributes errorAttributes() {
    return new CustomErrorAttributes();
  }

}
