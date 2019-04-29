package com.hitales.national.migrate.dao;

import com.hitales.national.migrate.entity.DoctorClinic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-26
 * @time:11:44
 */
public interface DoctorClinicDao extends JpaRepository<DoctorClinic, Long>{
    List<DoctorClinic> findByName(String name);

    List<DoctorClinic> findByClinicIdAndDepth(Integer clinicId, Integer depth);
}
