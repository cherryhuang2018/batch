package com.tsmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.RoleScop;

@Repository
public interface RoleScopRepository extends JpaRepository<RoleScop, String> {

  List<RoleScop> findByRoleidIn(List<String> roleidList);
}
