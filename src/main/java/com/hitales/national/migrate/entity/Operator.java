package com.hitales.national.migrate.entity;

import com.hitales.national.migrate.enums.OperatorAccountState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 *
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "login_operator", comment = "运营登录信息表")
@Table(name = "login_operator", indexes = { //
        @Index(name = "idx_login_opr_phone", columnList = "phone"),
        @Index(name = "idx_login_opr_id_no", columnList = "id_no")
})
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false)
public class Operator extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned  comment '系统ID'")
    private Long id;

    @Column(name = "id_no", columnDefinition = "varchar(18)  default '' comment '身份证号'")
    private String idNo;

    @Column(name = "id_name", columnDefinition = "varchar(35)  default '' comment '身份证姓名'")
    private String idName;

    @Column(name = "phone", columnDefinition = "varchar(20)  default '' comment '联系电话'")
    private String phone;

    @Column(name = "password", columnDefinition = "varchar(60) DEFAULT '' COMMENT '登录密码摘要(双MD5)'")
    private String password;

    @Column(name = "account_state", columnDefinition = "tinyint(1) unsigned default 1 comment '账户状态:1-启用;2-临时停用'")
    private OperatorAccountState accountState;

    @Column(name = "county_id", columnDefinition = "bigint(20) unsigned  comment '行政县区id'")
    private Long countyId;

    @Column(name = "username", columnDefinition = "varchar(30)  default '' comment '登录用户名'")
    private String username;

}
