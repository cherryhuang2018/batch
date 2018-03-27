package com.tsmc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsAuthenticationProvider
    extends AbstractUserDetailsAuthenticationProvider {

  private static final Logger logger =
      LoggerFactory.getLogger(CustomUserDetailsAuthenticationProvider.class);

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    logger.debug(
        ">> CustomUserDetailsAuthenticationProvider.additionalAuthenticationChecks userDetails={}, authentication={}",
        userDetails, authentication);
    if (authentication.getCredentials() == null || userDetails.getPassword() == null) {
      throw new BadCredentialsException("Credentials may not be null.");
    }
    // System.out.println(authentication.getCredentials() + "," + userDetails.getPassword());
    if (!passwordEncoder.matches((String) authentication.getCredentials(),
        userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid username or password");
    }
    logger.debug("<< CustomUserDetailsAuthenticationProvider.additionalAuthenticationChecks");
  }

  @Override
  protected UserDetails retrieveUser(String username,
      UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    logger.debug(
        ">> CustomUserDetailsAuthenticationProvider.retrieveUser username={}, authentication={}",
        username, authentication);
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
    logger.debug("< CustomUserDetailsAuthenticationProvider.retrieveUser UserDetails={}",
        userDetails);
    return userDetails;
  }
}

