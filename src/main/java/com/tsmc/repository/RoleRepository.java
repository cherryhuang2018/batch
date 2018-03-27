package com.tsmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  Role findByCode(String code);

  List<Role> findByRoleidIn(List<String> roleidList);

  List<Role> findByCodeIn(List<String> rolecodeList);
}
