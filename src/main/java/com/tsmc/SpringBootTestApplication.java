package com.tsmc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootTestApplication {

  public static void main(String[] args) {
     SpringApplication.run(SpringBootTestApplication.class, args);
  }
}
