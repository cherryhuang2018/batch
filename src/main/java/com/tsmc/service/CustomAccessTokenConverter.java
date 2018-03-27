package com.tsmc.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;

public class CustomAccessTokenConverter implements AccessTokenConverter {

  private static final Logger logger = LoggerFactory.getLogger(CustomAccessTokenConverter.class);

  private UserAuthenticationConverter userAuthenticationConverter =
      new DefaultUserAuthenticationConverter();

  private boolean includeGrantType;

  /**
   * Converter for the part of the data in the token representing a user.
   *
   * @param userTokenConverter the userTokenConverter to set
   */
  public void setUserTokenConverter(UserAuthenticationConverter userAuthenticationConverter) {
    logger.info(
        ">> CustomAccessTokenConverter.setUserTokenConverter UserAuthenticationConverter={}",
        userAuthenticationConverter);
    this.userAuthenticationConverter = userAuthenticationConverter;
  }

  /**
   * Flag to indicate the the grant type should be included in the converted token.
   *
   * @param includeGrantType the flag value (default false)
   */
  public void setIncludeGrantType(boolean includeGrantType) {
    logger.info(">> CustomAccessTokenConverter.setIncludeGrantType includeGrantType={}",
        includeGrantType);
    this.includeGrantType = includeGrantType;
  }

  public Map<String, ?> convertAccessToken(OAuth2AccessToken token,
      OAuth2Authentication authentication) {
    logger.info(">> CustomAccessTokenConverter.convertAccessToken token={}, authentication={}",
        token, authentication);
    Map<String, Object> response = new HashMap<String, Object>();
    OAuth2Request clientToken = authentication.getOAuth2Request();

    if (!authentication.isClientOnly()) {
      response.putAll(userAuthenticationConverter
          .convertUserAuthentication(authentication.getUserAuthentication()));
    } else {
      if (clientToken.getAuthorities() != null && !clientToken.getAuthorities().isEmpty()) {
        response.put(UserAuthenticationConverter.AUTHORITIES,
            AuthorityUtils.authorityListToSet(clientToken.getAuthorities()));
      }
    }

    if (token.getScope() != null) {
      response.put(SCOPE, token.getScope());
    }
    if (token.getAdditionalInformation().containsKey(JTI)) {
      response.put(JTI, token.getAdditionalInformation().get(JTI));
    }
    if (token.getExpiration() != null) {
      response.put(EXP, token.getExpiration().getTime() / 1000);
    }

    if (includeGrantType && authentication.getOAuth2Request().getGrantType() != null) {
      response.put(GRANT_TYPE, authentication.getOAuth2Request().getGrantType());
    }

    response.putAll(token.getAdditionalInformation());

    response.put(CLIENT_ID, clientToken.getClientId());
    if (clientToken.getResourceIds() != null && !clientToken.getResourceIds().isEmpty()) {
      response.put(AUD, clientToken.getResourceIds());
    }
    logger.info("<< CustomAccessTokenConverter.convertAccessToken response={}", response);
    return response;
  }

  public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
    logger.info(">> CustomAccessTokenConverter.extractAccessToken value={}, map={}", value, map);
    DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(value);
    Map<String, Object> info = new HashMap<String, Object>(map);
    info.remove(EXP);
    info.remove(AUD);
    info.remove(CLIENT_ID);
    info.remove(SCOPE);
    if (map.containsKey(EXP)) {
      token.setExpiration(new Date((Long) map.get(EXP) * 1000L));
    }
    if (map.containsKey(JTI)) {
      info.put(JTI, map.get(JTI));
    }
    token.setScope(extractScope(map));
    token.setAdditionalInformation(info);
    logger.info("<< CustomAccessTokenConverter.extractAccessToken DefaultOAuth2AccessToken={}",
        token);
    return token;
  }

  public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
    logger.info(">> CustomAccessTokenConverter.extractAuthentication map={}", map);
    Map<String, String> parameters = new HashMap<String, String>();
    Set<String> scope = extractScope(map);
    Authentication user = userAuthenticationConverter.extractAuthentication(map);
    String clientId = (String) map.get(CLIENT_ID);
    parameters.put(CLIENT_ID, clientId);
    if (includeGrantType && map.containsKey(GRANT_TYPE)) {
      parameters.put(GRANT_TYPE, (String) map.get(GRANT_TYPE));
    }
    Set<String> resourceIds = new LinkedHashSet<String>(
        map.containsKey(AUD) ? getAudience(map) : Collections.<String>emptySet());

    Collection<? extends GrantedAuthority> authorities = null;
    if (user == null && map.containsKey(AUTHORITIES)) {
      @SuppressWarnings("unchecked")
      String[] roles = ((Collection<String>) map.get(AUTHORITIES)).toArray(new String[0]);
      authorities = AuthorityUtils.createAuthorityList(roles);
    }
    OAuth2Request request = new OAuth2Request(parameters, clientId, authorities, true, scope,
        resourceIds, null, null, null);
    OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(request, user);
    logger.info("<< CustomAccessTokenConverter.extractAuthentication OAuth2Authentication={}",
        oAuth2Authentication);
    return oAuth2Authentication;
  }

  private Collection<String> getAudience(Map<String, ?> map) {
    logger.info(">> CustomAccessTokenConverter.getAudience map={}", map);
    Object auds = map.get(AUD);
    if (auds instanceof Collection) {
      @SuppressWarnings("unchecked")
      Collection<String> result = (Collection<String>) auds;
      return result;
    }
    Collection<String> collection = Collections.singleton((String) auds);
    logger.info("<< CustomAccessTokenConverter.getAudience Collection={}", collection);
    return collection;
  }

  private Set<String> extractScope(Map<String, ?> map) {
    logger.info(">> CustomAccessTokenConverter.extractScope map={}", map);
    Set<String> scope = Collections.emptySet();
    if (map.containsKey(SCOPE)) {
      Object scopeObj = map.get(SCOPE);
      if (String.class.isInstance(scopeObj)) {
        scope = new LinkedHashSet<String>(Arrays.asList(String.class.cast(scopeObj).split(" ")));
      } else if (Collection.class.isAssignableFrom(scopeObj.getClass())) {
        @SuppressWarnings("unchecked")
        Collection<String> scopeColl = (Collection<String>) scopeObj;
        scope = new LinkedHashSet<String>(scopeColl); // Preserve ordering
      }
    }
    logger.info("<< CustomAccessTokenConverter.extractScope scope={}", scope);
    return scope;
  }
}

