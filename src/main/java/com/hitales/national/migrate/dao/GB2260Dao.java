package com.hitales.national.migrate.dao;

import com.hitales.national.migrate.entity.GB2260;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface GB2260Dao extends JpaRepository<GB2260, Long>, JpaSpecificationExecutor<GB2260> {

   List<GB2260> findByCanonicalCodeAndDepth(Long canonicalCode, Integer depth);

   List<GB2260> findByCanonicalCode(Long canonicalCode);

   List<GB2260> findByNameAAndDepth(String name, Integer depth);

   List<GB2260> findByNameAndCanonicalCode(String name, Long canonicalCode);

}
