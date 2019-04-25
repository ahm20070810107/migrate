package com.hitales.national.migrate.dao;

import com.hitales.national.migrate.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CitizenDao extends JpaRepository<Citizen, Long>, JpaSpecificationExecutor<Citizen> {

}
