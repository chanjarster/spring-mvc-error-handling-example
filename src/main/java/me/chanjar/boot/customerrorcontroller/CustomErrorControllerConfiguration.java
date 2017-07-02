package me.chanjar.boot.customerrorcontroller;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomErrorControllerConfiguration {

  private final ServerProperties serverProperties;

  private final List<ErrorViewResolver> errorViewResolvers;

  public CustomErrorControllerConfiguration(ServerProperties serverProperties,
      ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
    this.serverProperties = serverProperties;
    this.errorViewResolvers = errorViewResolversProvider.getIfAvailable();
  }

  /**
   * 抄的是{@link ErrorMvcAutoConfiguration#basicErrorController(ErrorAttributes)}
   *
   * @param errorAttributes
   * @return
   */
  @Bean
  public CustomErrorController customErrorController(ErrorAttributes errorAttributes) {
    return new CustomErrorController(
        errorAttributes,
        this.serverProperties.getError(),
        this.errorViewResolvers
    );
  }

}
