package com.tsmc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.tsmc.service.CustomJdbcClientDetailsService;
import com.tsmc.service.CustomTokenServices;
import com.tsmc.service.CustomUserDetailsAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

  @Autowired
  private CustomUserDetailsAuthenticationProvider customUserDetailsAuthenticationProvider;
  @Autowired
  private CustomJdbcClientDetailsService customJdbcClientDetailsService;
  @Autowired
  private TokenStore jwtTokenStore;
  @Autowired
  private JwtAccessTokenConverter jwtAccessTokenConverter;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    logger.info(">> WebSecurityConfiguration.configure AuthenticationManagerBuilder={}", auth);
    auth.authenticationProvider(customUserDetailsAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
  }

  // @Bean
  // public TokenStore tokenStore() {
  // JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
  // return new CustomTokenStore();
  // }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  // @Bean
  // public JwtAccessTokenConverter jwtAccessTokenConverter() {
  // JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
  // jwtAccessTokenConverter.setAccessTokenConverter(new CustomAccessTokenConverter());
  // return jwtAccessTokenConverter;
  // }

  @Bean
  public CustomTokenServices customTokenServices() throws Exception {
    CustomTokenServices tokenServices = new CustomTokenServices();
    tokenServices.setAuthenticationManager(authenticationManagerBean());
    tokenServices.setTokenStore(jwtTokenStore);
    tokenServices.setAccessTokenEnhancer(jwtAccessTokenConverter);
    tokenServices.setClientDetailsService(customJdbcClientDetailsService);
    tokenServices.setSupportRefreshToken(true);
    return tokenServices;
  }
}
