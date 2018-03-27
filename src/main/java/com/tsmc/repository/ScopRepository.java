package com.tsmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.Scop;

@Repository
public interface ScopRepository extends JpaRepository<Scop, String> {

  List<Scop> findByResourceidIn(List<String> resourceidList);

  List<Scop> findByResourceidInAndScopidIn(List<String> resourceidList, List<String> scopidList);
}
