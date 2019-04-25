package com.hitales.national.migrate.entity;

import com.hitales.national.migrate.converter.ListLongConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * 医疗机构
 * parentId(上级机构ID) = 0 为顶层节点
 *
 * @author harryhe
 */
@Entity
@org.hibernate.annotations.Table(appliesTo = "doctor_clinic", comment = "医疗机构表")
@Table(name = "doctor_clinic", indexes = {
        @Index(name = "idx_clinic_id", columnList = "clinic_id"),
        @Index(name = "idx_clinic_county_id", columnList = "county_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = false)
public class DoctorClinic extends AuditableEntity {

    /**
     * 顶层节点 ClinicId
     */
    public static final Integer ROOT_PARENT = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "clinic_id", columnDefinition = "int(10) unsigned default 0 comment '机构ID'")
    private Integer clinicId;

    @Column(name = "[name]", columnDefinition = "varchar(20) default '' comment '机构名称'")
    private String name;

    @Column(name = "parent_id", columnDefinition = "int(10) unsigned default 0 comment '上级机构ID'")
    private Integer parentId;

    @Column(name = "[scope]", columnDefinition = "varchar(4000) default '' comment '辖区（多条以,分割)'")
    @Convert(converter = ListLongConverter.class)
    private List<Long> scope;

    @Column(name = "depth", columnDefinition = "tinyint unsigned default 0 comment '节点深度，根为0'")
    private Integer depth;

    @Column(name = "child_size", columnDefinition = "tinyint unsigned default 0 comment '直接下集节点数量'")
    private Integer childSize;

    @Column(name = "county_id", columnDefinition = "bigint(20) unsigned default 0 comment '行政县ID'")
    private Long countyId;


    public static DoctorClinic ofClinicId(Integer clinicId) {
        DoctorClinic doctorClinic = new DoctorClinic();
        doctorClinic.setClinicId(clinicId);
        return doctorClinic;
    }
}
