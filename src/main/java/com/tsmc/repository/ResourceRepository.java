package com.tsmc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tsmc.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {

  List<Resource> findByResourceidIn(List<String> resourceidList);
}
