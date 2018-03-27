package com.tsmc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "oauthtoken")
@EntityListeners(AuditingEntityListener.class) // 加這行 CreatedBy 才會生效
public class Oauthtoken {
  @Id
  @Column(name = "serid")
  private String serid;
  @Column(name = "tokenid")
  private String tokenid;
  @Column(name = "refreshid")
  private String refreshid;
  @Column(name = "clientid")
  private String clientid;
  @Column(name = "granttype")
  private String granttype;
  @Column(name = "resourceids")
  private String resourceids;
  @Column(name = "scopes")
  private String scopes;
  @Column(name = "username")
  private String username;
  @Column(name = "redirecturi")
  private String redirecturi;
  @Lob
  @Column(name = "accesstoken")
  private String accesstoken;
  @Lob
  @Column(name = "refreshtoken")
  private String refreshtoken;
  @Column(name = "refreshed")
  private Boolean refreshed;
  @Column(name = "locked")
  private Boolean locked;
  @Lob
  @Column(name = "authentication")
  private byte[] authentication;
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

  public String getSerid() {
    return serid;
  }

  public void setSerid(String serid) {
    this.serid = serid;
  }

  public String getTokenid() {
    return tokenid;
  }

  public void setTokenid(String tokenid) {
    this.tokenid = tokenid;
  }

  public String getRefreshid() {
    return refreshid;
  }

  public void setRefreshid(String refreshid) {
    this.refreshid = refreshid;
  }

  public String getClientid() {
    return clientid;
  }

  public void setClientid(String clientid) {
    this.clientid = clientid;
  }

  public String getGranttype() {
    return granttype;
  }

  public void setGranttype(String granttype) {
    this.granttype = granttype;
  }

  public String getResourceids() {
    return resourceids;
  }

  public void setResourceids(String resourceids) {
    this.resourceids = resourceids;
  }

  public String getScopes() {
    return scopes;
  }

  public void setScopes(String scopes) {
    this.scopes = scopes;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRedirecturi() {
    return redirecturi;
  }

  public void setRedirecturi(String redirecturi) {
    this.redirecturi = redirecturi;
  }

  public String getAccesstoken() {
    return accesstoken;
  }

  public void setAccesstoken(String accesstoken) {
    this.accesstoken = accesstoken;
  }

  public String getRefreshtoken() {
    return refreshtoken;
  }

  public void setRefreshtoken(String refreshtoken) {
    this.refreshtoken = refreshtoken;
  }

  public Boolean getRefreshed() {
    return refreshed;
  }

  public void setRefreshed(Boolean refreshed) {
    this.refreshed = refreshed;
  }

  public Boolean getLocked() {
    return locked;
  }

  public void setLocked(Boolean locked) {
    this.locked = locked;
  }

  public byte[] getAuthentication() {
    return authentication;
  }

  public void setAuthentication(byte[] authentication) {
    this.authentication = authentication;
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

