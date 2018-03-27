package com.tsmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.OauthClient;

@Repository
public interface OauthClientRepository extends JpaRepository<OauthClient, String> {

}
