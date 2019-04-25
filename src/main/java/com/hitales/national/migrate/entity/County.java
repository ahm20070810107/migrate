package com.hitales.national.migrate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "sys_county", comment = "行政县列表")
@Table(name = "sys_county", indexes = {
        @Index(name = "idx_county_domain", columnList = "domain_prefix", unique = true),
        @Index(name = "idx_county_location", columnList = "location", unique = true)
})
@Data
@EqualsAndHashCode(callSuper = false)
public class County extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(64) default '' comment '区县名称'")
    private String name;

    @Column(name = "[location]", columnDefinition = "bigint(15) unsigned default 0 comment '县级行政区划代码'")
    private Long location;

    @Column(name = "domain_prefix", columnDefinition = "varchar(20) default '' comment '域名前缀'")
    private String domainPrefix;


}