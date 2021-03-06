package com.tsmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tsmc.model.Account;
import com.tsmc.model.Role;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

  @Query("SELECT a FROM Account a WHERE a.username = :username")
  Account findByUsername(@Param("username") String username);

  @Query("SELECT r FROM Account a, AccountRole ar, Role r WHERE a.username = :username and a.accountid = ar.accountid and ar.roleid = r.roleid")
  List<Role> findRoleListByUsername(@Param("username") String username);
}

