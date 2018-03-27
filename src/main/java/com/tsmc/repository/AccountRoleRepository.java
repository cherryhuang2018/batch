package com.tsmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.AccountRole;

@Repository
public interface AccountRoleRepository extends JpaRepository<AccountRole, String> {

  List<AccountRole> findByAccountid(String accountid);
}
