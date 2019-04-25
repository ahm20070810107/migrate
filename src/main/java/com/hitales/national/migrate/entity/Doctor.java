package com.hitales.national.migrate.entity;

import com.hitales.national.migrate.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "doctor", comment = "医生信息表")
@Table(name = "doctor", indexes = { //
									  @Index(name = "idx_phone", columnList = "phone"),
									  @Index(name = "idx_id_no", columnList = "id_no")
})
@Data
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false)
public class Doctor extends AuditableEntity implements CopyPartially {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint(20) unsigned  comment '系统ID'")
	private Long id;

	@Column(name = "id_no", columnDefinition = "varchar(18)  default '' comment '身份证号'")
	private String idNo;

	@Column(name = "id_name", columnDefinition = "varchar(35)  default '' comment '身份证姓名'")
	private String idName;

	@Column(name = "id_image", columnDefinition = "varchar(150) DEFAULT '' COMMENT '身份证正面图片'")
	private String idImage;

	@Column(name = "gender", columnDefinition = "tinyint(2) DEFAULT 0 COMMENT '性别: 1-男;2-女;9-未说明'")
	private DoctorGender gender;

	@Column(name = "nation", columnDefinition = "tinyint(2) DEFAULT 0 COMMENT '民族: 见枚举内容 nation'")
	private Nation nation;

	@Column(name = "birthday", columnDefinition = "date comment '出生日期'")
	private Date birthday;

	@Column(name = "address", columnDefinition = "varchar(100)  default '' comment '家庭住址'")
	private String address;

	@Column(name = "phone", columnDefinition = "varchar(20)  default '' comment '联系电话'")
	private String phone;

	@Column(name = "clinic_id", columnDefinition = "int(10) unsigned default 0 comment '所属医疗机构ID'")
	private Integer clinicId;

	@Column(name = "avatar", columnDefinition = "varchar(150) DEFAULT '' COMMENT '头像图片'")
	private String avatar;

	@Column(name = "star", columnDefinition = "decimal(8,4) DEFAULT '0.0000' COMMENT '医生评价'")
	private Double star;

	@Column(name = "password", columnDefinition = "varchar(60) DEFAULT '' COMMENT '登录密码摘要(双MD5)'")
	private String password;

	@Column(name = "department", columnDefinition = "varchar(30) DEFAULT '' COMMENT '科室'")
	private String department;

	@Column(name = "doctor_title", columnDefinition = "tinyint(2) DEFAULT 0 COMMENT '职称:1-主任医师;2-副主任医师;3-主治医师;4-医师;5-助理医师;6-主任护师;7-副主任护师;8-主管护师;9-护师;10-护士;11-其他'")
	private DoctorTitle title;

	@Column(name = "work_since_year", columnDefinition = "int(4) DEFAULT 0 COMMENT '参加工作年份'")
	private Integer workSinceYear;

	@Column(name = "audit_state", columnDefinition = "tinyint(2) DEFAULT 0 COMMENT '登录审核状态:1-待审核;2-审核通过;3-审核拒绝;'")
	private DoctorAuditState auditState;// 审核状态

	@Column(name = "audit_deny_time", columnDefinition = "datetime comment '最近一次审核拒绝时间'")
	private Date auditDenyTime;

	@Column(name = "certificate_image", columnDefinition = "varchar(150) DEFAULT '' COMMENT '医师资格证书图片'")
	private String certificateImage;

	@Column(name = "practising_certificate_image", columnDefinition = "varchar(150) DEFAULT '' COMMENT '医师执业证书图片'")
	private String practisingCertificateImage;

	@Column(name = "signature_image", columnDefinition = "varchar(150) DEFAULT '' COMMENT '签名图片'")
	private String signatureImage;

	@Column(name = "account_state", columnDefinition = "tinyint(1) unsigned default 1 comment '账户状态:1-启用;2-临时停用;3-停用'")
	private DoctorAccountState accountState;

	@Column(name = "roles", columnDefinition = "tinyint(2) DEFAULT 0 comment '医生角色：4-院长;5-卫计局管理员;村医、乡医、专家通过医疗机构动态计算'")
	private DoctorRole role;

}
