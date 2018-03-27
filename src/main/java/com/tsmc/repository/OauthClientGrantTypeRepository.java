package com.tsmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.OauthClientGrantType;

@Repository
public interface OauthClientGrantTypeRepository
    extends
      JpaRepository<OauthClientGrantType, String> {
  List<OauthClientGrantType> findByClientid(String clientid);
}

