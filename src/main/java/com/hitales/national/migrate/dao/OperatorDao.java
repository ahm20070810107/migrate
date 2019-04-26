package com.hitales.national.migrate.dao;

import com.hitales.national.migrate.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author:huangming
 * @date:2019-04-26
 * @time:11:44
 */
public interface OperatorDao extends JpaRepository<Operator, Long>, JpaSpecificationExecutor<Operator> {
    List<Operator> findByUsername(String username);
}
