package com.tsmc.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tsmc.model.Account;
import com.tsmc.model.AccountRole;
import com.tsmc.model.Role;
import com.tsmc.repository.AccountRepository;
import com.tsmc.repository.AccountRoleRepository;
import com.tsmc.repository.RoleRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private AccountRoleRepository accountRoleRepository;
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info(">> CustomUserDetailsService.loadUserByUsername username={}", username);
    Account account = accountRepository.findByUsername(username);

    if (account == null) {
      // Not found...
      throw new BadCredentialsException("Invalid username or password");
    }

    List<AccountRole> accountRoles = accountRoleRepository.findByAccountid(account.getAccountid());
    if (accountRoles == null || accountRoles.isEmpty()) {
      // No Roles assigned to user...
      throw new UsernameNotFoundException("User not authorized.");
    }
    // 取出角色清單
    List<String> roleidList = new ArrayList<String>();
    for (AccountRole ar : accountRoles) {
      roleidList.add(ar.getRoleid());
    }

    List<Role> roleList = roleRepository.findByRoleidIn(roleidList);
    List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
    for (Role role : roleList) {
      grantedAuthorities.add(new SimpleGrantedAuthority(role.getCode()));
    }

    User userDetail =
        new User(account.getUsername(), account.getPassword(), account.isEnabled(), // 是否可用
            !account.isExpired(), // 是否過期
            !account.isCredentialsexpired(), // 證書不過期為true
            !account.isLocked(), // 帳號未鎖定為true
            grantedAuthorities);

    logger.info("<< CustomUserDetailsService.loadUserByUsername User={}", userDetail);
    return userDetail;
  }
}
