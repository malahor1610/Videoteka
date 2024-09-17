package com.github.malahor.videoteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;

@SpringBootApplication(
    exclude = {
      AopAutoConfiguration.class,
      MultipartAutoConfiguration.class,
      GsonAutoConfiguration.class,
      RestTemplateAutoConfiguration.class,
      RestClientAutoConfiguration.class,
      ErrorMvcAutoConfiguration.class,
      WebSocketServletAutoConfiguration.class,
    })
public class VideotekaApplication {

  public static void main(String[] args) {
    SpringApplication.run(VideotekaApplication.class, args);
  }
}
