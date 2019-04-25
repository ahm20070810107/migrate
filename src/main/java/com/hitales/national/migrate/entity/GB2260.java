package com.hitales.national.migrate.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "sys_gb2260", comment = "行政区划表")
@Table(name = "sys_gb2260", indexes = { //
										  @Index(name = "sys_gb2260_canonical", columnList = "canonical_code, depth", unique = true),
})
@Data
@EqualsAndHashCode(callSuper = false)
public class GB2260 extends AuditableEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
	private Long id;

	@Column(name = "canonical_code", columnDefinition = "bigint(15) unsigned not null default 0 comment '行政区划代码全'")
	private Long canonicalCode;

	@Column(name = "depth", columnDefinition = "tinyint(1) unsigned default 0 comment '树状结构深度'")
	private Integer depth;

	@Column(name = "name", columnDefinition = "varchar(40) default '' comment '名称'")
	private String name;


}