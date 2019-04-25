package com.hitales.national.migrate.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 是否支持ID为主键类型的实体记录
 *
 * @author harryhe
 */
@SuppressWarnings("serial")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Data
public abstract class AuditableEntity implements Serializable {

	@Column(name = "create_time", columnDefinition = "datetime default  CURRENT_TIMESTAMP  comment '记录创建时间'")
	@CreatedDate
	private Date createTime;

	@Column(name = "update_time", columnDefinition = "datetime default CURRENT_TIMESTAMP comment '记录更新时间'")
	@LastModifiedDate
	private Date updateTime;

}
