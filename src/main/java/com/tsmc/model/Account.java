package com.tsmc.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class) // 加這行 CreatedBy 才會生效
public class Account {
  @Id
  private String accountid;

  @NotNull
  @Size(min = 8, max = 255, message = "Username have to be grater than 8 characters")
  @Column(unique = true)
  private String username;

  @NotNull
  private String email;

  @NotNull
  @Size(min = 8, max = 255, message = "Password have to be grater than 8 characters")
  private String password;

  @NotNull
  private boolean enabled = true;

  @NotNull
  private boolean credentialsexpired = false;

  @NotNull
  private boolean expired = false;

  @NotNull
  private boolean locked = false;
  //
  // @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  // @JoinTable(name = "AccountRole", joinColumns = @JoinColumn(name = "accountid",
  // referencedColumnName = "accountid"),
  // inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleid"))
  // private List<Role> roles;

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

  public String getAccountid() {
    return accountid;
  }

  public void setAccountid(String accountid) {
    this.accountid = accountid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isCredentialsexpired() {
    return credentialsexpired;
  }

  public void setCredentialsexpired(boolean credentialsexpired) {
    this.credentialsexpired = credentialsexpired;
  }

  public boolean isExpired() {
    return expired;
  }

  public void setExpired(boolean expired) {
    this.expired = expired;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
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

