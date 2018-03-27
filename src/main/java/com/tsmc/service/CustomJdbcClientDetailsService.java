package com.tsmc.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsmc.model.OauthClient;
import com.tsmc.model.OauthClientGrantType;
import com.tsmc.model.Resource;
import com.tsmc.model.Scop;
import com.tsmc.repository.OauthClientGrantTypeRepository;
import com.tsmc.repository.OauthClientRepository;
import com.tsmc.repository.OauthClientResourceRepository;
import com.tsmc.repository.ResourceRepository;
import com.tsmc.repository.ScopRepository;

@Component
public class CustomJdbcClientDetailsService
    implements
      ClientDetailsService,
      ClientRegistrationService {

  private static final Logger logger = LoggerFactory.getLogger(CustomJdbcClientDetailsService.class);

  @Autowired
  private OauthClientRepository oauthClientRepository;
  @Autowired
  private OauthClientResourceRepository oauthClientResourceRepository;
  @Autowired
  private ResourceRepository resourceRepository;
  @Autowired
  private ScopRepository scopRepository;
  @Autowired
  private OauthClientGrantTypeRepository oauthClientGrantTypeRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    logger.debug(">> CustomJdbcClientDetailsService.loadClientByClientId clientId={}", clientId);
    OauthClient oauthClient = oauthClientRepository.findOne(clientId);
    // 取出 Resource
    List<String> resourceidList =
        oauthClientResourceRepository.findResourceidByClientid(oauthClient.getClientid());
    // 雖然有對應，但避免對應錯還是去 Resource 表格取出來
    List<Resource> resourceList = resourceRepository.findByResourceidIn(resourceidList);
    List<String> resourceids =
        resourceList.stream().map(Resource::getResourceid).collect(Collectors.toList());
    // 取出 Scop
    List<Scop> scopList = scopRepository.findByResourceidIn(resourceids);
    List<String> scops = scopList.stream().map(Scop::getScopcode).collect(Collectors.toList());
    // 取出授權類型
    List<OauthClientGrantType> oauthClientGrantTypeList =
        oauthClientGrantTypeRepository.findByClientid(oauthClient.getClientid());
    List<String> grantTypes = oauthClientGrantTypeList.stream()
        .map(OauthClientGrantType::getGranttype).collect(Collectors.toList());
    BaseClientDetails details = new BaseClientDetails();
    details.setClientId(oauthClient.getClientid());
    details.setClientSecret(oauthClient.getClientSecret());
    details.setResourceIds(resourceids);
    details.setScope(scops);
    details.setAuthorizedGrantTypes(grantTypes);
    details.setAccessTokenValiditySeconds(oauthClient.getAccessTokenValidity());
    details.setRefreshTokenValiditySeconds(oauthClient.getRefreshTokenValidity());

    // 這不用設定，在帳號驗證那邊會給值 CustomUserDetailsService.loadUserByUsername
    // details.setAuthorities(authorities);
    // 這邊會給這個 client 所有可申請的範圍，在Token轉換的時候把不適合該角色權限踢掉
    details.setAutoApproveScopes(scops);
    if (StringUtils.hasText(oauthClient.getWebServerRedirectUri())) {
      details.setRegisteredRedirectUri(Arrays
          .stream(oauthClient.getWebServerRedirectUri().split(",")).collect(Collectors.toSet()));
    }
    if (StringUtils.hasText(oauthClient.getAdditionalInformation())) {
      try {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> additionalInformation =
            mapper.readValue(oauthClient.getAdditionalInformation(), Map.class);
        // System.out.println("has set additionalInformation="+additionalInformation);
        details.setAdditionalInformation(additionalInformation);
      } catch (Exception e) {
        logger.error("AdditionalInformation to Map error", e);
      }
    }

    return details;
  }

  @Override
  public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
    logger.debug(">> CustomJdbcClientDetailsService.addClientDetails clientDetails={}", clientDetails);
  }

  @Override
  public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
    logger.debug(">> CustomJdbcClientDetailsService.updateClientDetails clientDetails={}",
        clientDetails);
  }

  @Override
  public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
    logger.debug(">> CustomJdbcClientDetailsService.updateClientSecret clientId={}, secret={}",
        clientId, secret);
  }

  @Override
  public void removeClientDetails(String clientId) throws NoSuchClientException {
    logger.debug(">> CustomJdbcClientDetailsService.removeClientDetails clientId={}", clientId);
  }

  @Override
  public List<ClientDetails> listClientDetails() {
    logger.debug(">> CustomJdbcClientDetailsService.listClientDetails");
    return null;
  }
}

