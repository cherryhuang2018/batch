package com.tsmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.Oauthtoken;

@Repository
public interface OauthtokenRepository extends JpaRepository<Oauthtoken, String> {

    Oauthtoken findByTokenid(String tokenid);

    Oauthtoken findByRefreshid(String refreshid);
}