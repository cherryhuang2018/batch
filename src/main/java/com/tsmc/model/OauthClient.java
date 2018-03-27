package com.tsmc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "oauth_client_details")
@EntityListeners(AuditingEntityListener.class) // 加這行 CreatedBy 才會生效
public class OauthClient {
  @Id
  private String clientid;
  @Column(name = "client_secret")
  private String clientSecret;
  @Column(name = "web_server_redirect_uri")
  private String webServerRedirectUri;
  @Column(name = "access_token_validity")
  private Integer accessTokenValidity;
  @Column(name = "refresh_token_validity")
  private Integer refreshTokenValidity;
  @Column(name = "additional_information")
  private String additionalInformation;

  private String autoapprove;

  @CreatedDate
  @Column(name = "createddate")
  private Date createddate;
  @CreatedBy
  @Column(name = "createdby")
  private String createdby;
  @LastModifiedDate
  @Column(name = "lastmodifieddate")
  private Date lastmodifieddate;
  @LastModifiedBy
  @Column(name = "lastmodifiedby")
  private String lastmodifiedby;

  public String getClientid() {
    return clientid;
  }

  public void setClientid(String clientid) {
    this.clientid = clientid;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }

  public String getWebServerRedirectUri() {
    return webServerRedirectUri;
  }

  public void setWebServerRedirectUri(String webServerRedirectUri) {
    this.webServerRedirectUri = webServerRedirectUri;
  }

  public Integer getAccessTokenValidity() {
    return accessTokenValidity;
  }

  public void setAccessTokenValidity(Integer accessTokenValidity) {
    this.accessTokenValidity = accessTokenValidity;
  }

  public Integer getRefreshTokenValidity() {
    return refreshTokenValidity;
  }

  public void setRefreshTokenValidity(Integer refreshTokenValidity) {
    this.refreshTokenValidity = refreshTokenValidity;
  }

  public String getAdditionalInformation() {
    return additionalInformation;
  }

  public void setAdditionalInformation(String additionalInformation) {
    this.additionalInformation = additionalInformation;
  }

  public String getAutoapprove() {
    return autoapprove;
  }

  public void setAutoapprove(String autoapprove) {
    this.autoapprove = autoapprove;
  }

  public Date getCreateddate() {
    return createddate;
  }

  public void setCreateddate(Date createddate) {
    this.createddate = createddate;
  }

  public String getCreatedby() {
    return createdby;
  }

  public void setCreatedby(String createdby) {
    this.createdby = createdby;
  }

  public Date getLastmodifieddate() {
    return lastmodifieddate;
  }

  public void setLastmodifieddate(Date lastmodifieddate) {
    this.lastmodifieddate = lastmodifieddate;
  }

  public String getLastmodifiedby() {
    return lastmodifiedby;
  }

  public void setLastmodifiedby(String lastmodifiedby) {
    this.lastmodifiedby = lastmodifiedby;
  }

}

