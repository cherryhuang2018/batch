package com.tsmc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.tsmc.service.CustomAccessTokenConverter;

@Configuration
public class JwtTokenConfig {

  private final Logger logger = LoggerFactory.getLogger(JwtTokenConfig.class);
  @Value("${security.oauth2.resource.jwt.key-value}")
  private String defaultJwtSigningKey;

  public JwtTokenConfig() {
    logger.info("Loading JwtTokenConfig ...");
  }

  @Bean
  public TokenStore jwtTokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    // JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    // jwtAccessTokenConverter.setSigningKey(defaultJwtSigningKey);
    JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
    jwtAccessTokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
    return jwtAccessTokenConverter;
  }

}
