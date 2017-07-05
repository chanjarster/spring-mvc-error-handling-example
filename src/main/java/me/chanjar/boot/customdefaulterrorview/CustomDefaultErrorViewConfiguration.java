package me.chanjar.boot.customdefaulterrorview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.Locale;

@Configuration
public class CustomDefaultErrorViewConfiguration {

  @Autowired
  private ThymeleafViewResolver thymeleafViewResolver;

  @Bean
  public View error() throws Exception {
    return thymeleafViewResolver.resolveViewName("custom-error-page/error", Locale.CHINA);
  }

}
