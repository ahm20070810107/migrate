package com.hitales.national.migrate.dao;

import com.hitales.national.migrate.entity.County;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CountyDao extends JpaRepository<County, Long>{

   List<County> findByNameAndLocation(String name, Long location);
   Optional<County> findByLocation(Long location);
}
