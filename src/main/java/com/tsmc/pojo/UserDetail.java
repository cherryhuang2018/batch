package com.tsmc.pojo;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class UserDetail {

  public UserDetail() {

  }

  public UserDetail(String userName, String password, boolean isEnabled, boolean isExpired,
      boolean isCredentialsexpired, boolean isLocked, List<GrantedAuthority> grantedAuthorities) {
    this.userName = userName;
    this.password = password;
    this.isEnabled = isEnabled;
    this.isExpired = isExpired;
    this.isCredentialsexpired = isCredentialsexpired;
    this.isLocked = isLocked;
    this.grantedAuthorities = grantedAuthorities;
  }

  private String userName;

  private String password;

  private boolean isEnabled;

  private boolean isExpired;

  private boolean isCredentialsexpired;

  private boolean isLocked;

  private List<GrantedAuthority> grantedAuthorities;


  public String getUserName() {
    return userName;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }


  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public boolean isEnabled() {
    return isEnabled;
  }


  public void setEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }


  public boolean isExpired() {
    return isExpired;
  }


  public void setExpired(boolean isExpired) {
    this.isExpired = isExpired;
  }


  public boolean isCredentialsexpired() {
    return isCredentialsexpired;
  }


  public void setCredentialsexpired(boolean isCredentialsexpired) {
    this.isCredentialsexpired = isCredentialsexpired;
  }


  public boolean isLocked() {
    return isLocked;
  }


  public void setLocked(boolean isLocked) {
    this.isLocked = isLocked;
  }


  public List<GrantedAuthority> getGrantedAuthorities() {
    return grantedAuthorities;
  }


  public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
    this.grantedAuthorities = grantedAuthorities;
  }

}
