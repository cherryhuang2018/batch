package com.tsmc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

import com.tsmc.service.CustomJdbcClientDetailsService;
import com.tsmc.service.CustomTokenServices;
import com.tsmc.service.CustomUserDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private CustomJdbcClientDetailsService customJdbcClientDetailsService;
  @Autowired
  private CustomTokenServices customTokenServices;

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints.authenticationManager(authenticationManager)
        .userDetailsService(customUserDetailsService).tokenServices(customTokenServices);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(customJdbcClientDetailsService);
  }

}
