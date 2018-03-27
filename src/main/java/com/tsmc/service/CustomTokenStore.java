package com.tsmc.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.tsmc.model.Oauthtoken;
import com.tsmc.repository.OauthtokenRepository;

public class CustomTokenStore implements TokenStore {
  
  private static final Logger logger = LoggerFactory.getLogger(CustomTokenStore.class);
  
  @Autowired
  private OauthtokenRepository oauthtokenRepository;

  @Override
  public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
    logger.info(">> CustomTokenStore.readAuthentication token={}", token);
    return null;
  }

  @Override
  public OAuth2Authentication readAuthentication(String token) {
    logger.info(">> CustomTokenStore.readAuthentication token={}", token);
    return null;
  }

  /**
   * 儲存 Token ，Auth 跟 Refresh 都會使用
   *
   * @param token
   * @param authentication
   */
  @Override
  public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
    logger.info(">> CustomTokenStore.storeAccessToken token={}, authentication={}", token,
        authentication);
    Oauthtoken oauthtoken = new Oauthtoken();
    String serid = UUID.randomUUID().toString();
    oauthtoken.setSerid(serid);
    oauthtoken.setTokenid(DigestUtils.md5Hex(token.getValue()));
    oauthtoken.setRefreshid(token.getRefreshToken() == null
        ? null
        : DigestUtils.md5Hex(token.getRefreshToken().getValue()));
    oauthtoken.setClientid(authentication.getOAuth2Request().getClientId());
    oauthtoken.setGranttype(authentication.getOAuth2Request().getGrantType());
    oauthtoken.setResourceids(authentication.getOAuth2Request().getResourceIds().toString());
    oauthtoken.setScopes(authentication.getOAuth2Request().getScope().toString());
    oauthtoken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
    oauthtoken.setRedirecturi(authentication.getOAuth2Request().getRedirectUri());
    oauthtoken.setAccesstoken(token.getValue());
    oauthtoken.setRefreshtoken(
        token.getRefreshToken() == null ? null : token.getRefreshToken().getValue());
    oauthtoken.setRefreshed(Boolean.FALSE);
    oauthtoken.setLocked(Boolean.FALSE);
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream oos = new ObjectOutputStream(baos);
      oos.writeObject(authentication);
      oos.flush();
      oauthtoken.setAuthentication(baos.toByteArray());
    } catch (IOException e) {
      logger.error("OAuth2Authentication serialization error", e);
    }
    oauthtokenRepository.save(oauthtoken);
    logger.info("<< CustomTokenStore.storeAccessToken");
  }

  @Override
  public OAuth2AccessToken readAccessToken(String tokenValue) {
    logger.info(">> CustomTokenStore.readAccessToken tokenValue={}", tokenValue);
    return null;
  }

  @Override
  public void removeAccessToken(OAuth2AccessToken token) {
    logger.info(">> CustomTokenStore.removeAccessToken token={}", token);
  }

  @Override
  public void storeRefreshToken(OAuth2RefreshToken refreshToken,
      OAuth2Authentication authentication) {
    logger.info(">> CustomTokenStore.storeRefreshToken refreshToken={}, authentication={}",
        refreshToken, authentication);
  }

  /**
   * 讀取 RefreshToken 這段只有在 Refresh 的時候被呼叫到，並且做限制 RefreshToken 使用過後就不能在取得新的 Token
   *
   * @param tokenValue
   * @return
   */
  @Override
  public OAuth2RefreshToken readRefreshToken(String tokenValue) {
    logger.info(">> CustomTokenStore.readRefreshToken tokenValue={}", tokenValue);
    Oauthtoken oauthtoken = oauthtokenRepository.findByRefreshid(DigestUtils.md5Hex(tokenValue));
    if (oauthtoken.getRefreshed() == Boolean.TRUE) {
      throw new BadCredentialsException("RefreshToken Is Refreshed.");
    }
    OAuth2RefreshToken oAuth2RefreshToken =
        new DefaultOAuth2RefreshToken(oauthtoken.getRefreshtoken());
    logger.info("<< CustomTokenStore.readRefreshToken OAuth2RefreshToken={}", oAuth2RefreshToken);
    return oAuth2RefreshToken;
  }

  /**
   * 讀取當初的授權資料才能再核發 Token
   *
   * @param token
   * @return
   */
  @Override
  public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
    logger.info(">> CustomTokenStore.readAuthenticationForRefreshToken token={}", token);
    OAuth2Authentication oAuth2Authentication = null;
    Oauthtoken oauthtoken =
        oauthtokenRepository.findByRefreshid(DigestUtils.md5Hex(token.getValue()));
    try {
      ObjectInputStream ois =
          new ObjectInputStream(new ByteArrayInputStream(oauthtoken.getAuthentication()));
      oAuth2Authentication = (OAuth2Authentication) ois.readObject();
    } catch (Exception e) {
      logger.error("OAuth2Authentication Deserialization error", e);
    }
    logger.info("<< CustomTokenStore.readAuthenticationForRefreshToken oAuth2Authentication={}",
        oAuth2Authentication);
    return oAuth2Authentication;
  }

  @Override
  public void removeRefreshToken(OAuth2RefreshToken token) {
    logger.info(">> CustomTokenStore.removeRefreshToken token={}", token);
  }

  /**
   * 當授權成功後回頭把 refreshToken 標記為已更新核發過
   *
   * @param refreshToken
   */
  @Override
  public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
    logger.info(">> CustomTokenStore.removeAccessTokenUsingRefreshToken refreshToken={}",
        refreshToken);
    Oauthtoken oauthtoken =
        oauthtokenRepository.findByRefreshid(DigestUtils.md5Hex(refreshToken.getValue()));
    oauthtoken.setRefreshed(Boolean.TRUE);
    oauthtokenRepository.save(oauthtoken);
    logger.info("<< CustomTokenStore.removeAccessTokenUsingRefreshToken");
  }

  @Override
  public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
    logger.info(">> CustomTokenStore.getAccessToken authentication={}", authentication);
    return null;
  }

  @Override
  public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId,
      String userName) {
    logger.info(">> CustomTokenStore.findTokensByClientIdAndUserName clientId={}, userName={}",
        clientId, userName);
    logger.info("<< CustomTokenStore.findTokensByClientIdAndUserName Collection={}", "[]");
    return Collections.emptySet();
  }

  @Override
  public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
    logger.info(">> CustomTokenStore.findTokensByClientId clientId={}", clientId);
    return null;
  }
}
