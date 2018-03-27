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
@Table(name = "oauth_client_resource")
@EntityListeners(AuditingEntityListener.class) // 加這行 CreatedBy 才會生效
public class OauthClientResource {
  @Id
  private String serid;
  private String clientid;
  private String resourceid;

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

  public String getClientid() {
    return clientid;
  }

  public void setClientid(String clientid) {
    this.clientid = clientid;
  }

  public String getResourceid() {
    return resourceid;
  }

  public void setResourceid(String resourceid) {
    this.resourceid = resourceid;
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

