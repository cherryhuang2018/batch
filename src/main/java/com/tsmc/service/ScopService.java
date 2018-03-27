package com.tsmc.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsmc.model.Role;
import com.tsmc.model.RoleScop;
import com.tsmc.model.Scop;
import com.tsmc.repository.RoleRepository;
import com.tsmc.repository.RoleScopRepository;
import com.tsmc.repository.ScopRepository;

@Service
public class ScopService {
  @Autowired
  private RoleRepository roleRepository;
  @Autowired
  private RoleScopRepository roleScopRepository;
  @Autowired
  private ScopRepository scopRepository;

  public Set<String> generationByRole(List<String> resourceIds, List<String> roleCodes) {
    // 先用角色代碼取出角色物件
    List<Role> roles = roleRepository.findByCodeIn(roleCodes);
    // 轉換成角色ID
    List<String> roleIds = new ArrayList<String>();
    for (Role role : roles) {
      roleIds.add(role.getRoleid());
    }
    // 用角色ID 找出對應的 Scop 對應表
    List<RoleScop> roleScops = roleScopRepository.findByRoleidIn(roleIds);
    // 取出 Scop ID
    List<String> scopIds = new ArrayList<String>();
    for (RoleScop rc : roleScops) {
      scopIds.add(rc.getScopid());
    }
    // 去 Scop 表格找出可用 resourceid 跟 scopid
    List<Scop> scops = scopRepository.findByResourceidInAndScopidIn(resourceIds, scopIds);
    // 轉成 scopcode
    Set<String> scopSet = new HashSet<String>();
    for (Scop s : scops) {
      scopSet.add(s.getScopcode());
    }
    return scopSet;
  }
}

