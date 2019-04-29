package com.hitales.national.migrate.entity;

import com.google.common.collect.Lists;
import com.hitales.commons.enums.YesNo;
import com.hitales.commons.util.IntInstant;
import com.hitales.national.migrate.converter.ListDisabilityStateConverter;
import com.hitales.national.migrate.converter.ListPoorCauseConverter;
import com.hitales.national.migrate.enums.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.*;
import java.util.*;

@Entity
@org.hibernate.annotations.Table(appliesTo = "citizen", comment = "居民基本信息")
@Table(name = "citizen", indexes = {
        @Index(name = "idx_location", columnList = "location")
})
@DynamicUpdate
@Data
@EqualsAndHashCode(callSuper = false)
public class Citizen extends AuditableEntity implements CopyPartially {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint(20) unsigned comment '系统ID'")
    private Long id;

    @Column(name = "id_type", columnDefinition = "tinyint(2) unsigned default 0 comment '证件类型:1-身份证;2-出生证明'")
    private IdType idType;

    @Column(name = "id_no", columnDefinition = "varchar(20) default '' comment '证件号码'")
    private String idNo;

    @Column(name = "id_name", columnDefinition = "varchar(30) default '' comment '证件姓名'")
    private String idName;

    @Column(name = "id_state", columnDefinition = "tinyint(2) unsigned default 0 comment '身份状态:1-有效身份;2-临时身份'")
    private IdState idState = IdState.AVAILABLE;

    @Column(name = "birth_image", columnDefinition = "varchar(150) default '' comment '出生证明图片'")
    private String birthImage;

    @Column(name = "audit_state", columnDefinition = "tinyint(2) unsigned default 1 comment '审核状态:1-待审核;2-审核通过;3-审核拒绝;'")
    private CitizenAuditState auditState = CitizenAuditState.APPROVED;

    @Column(name = "gender", columnDefinition = "tinyint(2) unsigned default 0 comment '性别:0-未知;1-男;2-女;9-未说明'")
    private CitizenGender gender = CitizenGender.UNKNOWN;

    @Column(name = "birthday", columnDefinition = "date comment '出生日期'")
    private Date birthday;

    @Column(name = "nation", columnDefinition = "tinyint(3) unsigned default 0 comment '民族:详见枚举内容列表'")
    private Nation nation;

    @Column(name = "blood_type_abo", columnDefinition = "tinyint(2) unsigned default 0 comment 'ABO血型:1-A型;2-B型;3-O型;4-AB型;5-不详;'")
    private BloodTypeABO bloodTypeAbo;

    @Column(name = "blood_type_rh", columnDefinition = "tinyint(2) unsigned default 0 comment 'RH血型:1-阴型;2-阳性;3-不详'")
    private BloodTypeRH bloodTypeRh;

    @Column(name = "education_degree", columnDefinition = "tinyint(2) unsigned default 0 comment '文化程度:1-研究生;2-大学本科;3-大学专科和专科学校;4-中等专业学校;5-技工学校;6-高中;7-初中;8-小学;9-文盲或半文盲;10-不详'")
    private EducationDegree educationDegree;

    @Column(name = "occupation_type", columnDefinition = "tinyint(2) unsigned default 0 comment '职业分类:0-国家机关、党群组织、企业、事业单位负责人;1-专业技术人员;2-办事人员和有关人员;3-商业、服务业人员;4-农、林、牧、渔、水利业生产人员;5-生产、运输设备操作人员及有关人员;6-军人;7-不便分类的其他从业人员;8-无职业'")
    private OccupationType occupationType;

    @Column(name = "marital_state", columnDefinition = "tinyint(2) unsigned default 0 comment '婚姻状况:1-未婚;2-已婚;3-丧偶;4-离婚'")
    private MaritalState maritalState;

    @Column(name = "phone", columnDefinition = "varchar(20) default '' comment '本人电话'")
    private String phone;

    @Column(name = "resident_type", columnDefinition = "tinyint(2) unsigned default 0 comment '常住类型:1-户籍;2-非户籍'")
    private ResidentType residentType;

    @Column(name = "work_unit", columnDefinition = "varchar(100) default '' comment '工作单位'")
    private String workUnit;

    @Column(name = "contact_phone", columnDefinition = "varchar(15) default '' comment '联系人电话'")
    private String contactPhone;

    @Column(name = "contact_name", columnDefinition = "varchar(30) default '' comment '联系人姓名'")
    private String contactName;

    @Column(name = "medical_payment", columnDefinition = "tinyint(3) unsigned default 0 comment '医疗费用支付方式:1-城镇职工基本医疗保险;2-城镇居民基本医疗保险;3-新型农村合作医疗;4-贫困救助;5-商业医疗保险;6-全公费;7-全自费;8-其他'")
    private MedicalPayment medicalPayment;

    @Column(name = "location", columnDefinition = "bigint(15) unsigned default 0 comment '所在地'")
    private Long location;

    @Column(name = "address", columnDefinition = "varchar(200)  default '' comment '家庭住址'")
    private String address;

    @Column(name = "[poor_cause]", columnDefinition = "varchar(64) default '' comment '致贫原因（多条以,分割):1-因病;2-因残;3-因学;4-因灾;5-因婚;6-缺土地;7-缺水;8-缺技术;9-缺劳力;10-缺资金;11-交通条件落后;12-自身发展动力不足'")
    @Convert(converter = ListPoorCauseConverter.class)
    private List<PoorCause> poorCause;

    @Column(name = "[disability_state]", columnDefinition = "varchar(64) default '' comment '残疾情况（多条以,分割):1-无残疾;2-视力残疾;3-听力残疾;4-言语残疾;5-肢体残疾;6-智力残疾;7-精神残疾;8-其他残疾'")
    @Convert(converter = ListDisabilityStateConverter.class)
    private List<DisabilityState> disabilityState;


    @Column(name = "crowd_hypertension", columnDefinition = "tinyint(1) unsigned default 0 comment '是否高血压患者:1-是;0-否;'")
    private YesNo crowdHypertension;

    @Column(name = "crowd_diabetes_ii", columnDefinition = "tinyint(1) unsigned default 0 comment '是否2型糖尿病患者:1-是;0-否;'")
    private YesNo crowdDiabetes2;

    @Column(name = "crowd_mental_disorder", columnDefinition = "tinyint(1) unsigned default 0 comment '是否严重精神障碍患者:1-是;0-否;'")
    private YesNo crowdMentalDisorder;

    @Column(name = "crowd_pulmonary_tuberculosis", columnDefinition = "tinyint(1) unsigned default 0 comment '是否肺结核患者:1-是;0-否;'")
    private YesNo crowdPulmonaryTuberculosis;

    @Column(name = "crowd_maternal", columnDefinition = "tinyint(1) unsigned default 0 comment '是否孕产妇:1-是;0-否;'")
    private YesNo crowdMaternal;

    @Column(name = "crowd_elder", columnDefinition = "tinyint(1) unsigned default 0 comment '是否65岁及以上老年人:1-是;0-否;'")
    private YesNo crowdElder;

    @Column(name = "crowd_child", columnDefinition = "tinyint(1) unsigned default 0 comment '是否0-6岁儿童:1-是;0-否;'")
    private YesNo crowdChild;

    @Column(name = "crowd_poor", columnDefinition = "tinyint(1) unsigned default 0 comment '是否贫困户:1-是;0-否;'")
    private YesNo crowdPoor;

    @Column(name = "crowd_disabled", columnDefinition = "tinyint(1) unsigned default 0 comment '是否残疾人:1-是;0-否;'")
    private YesNo crowdDisabled;

    @Column(name = "crowd_infection", columnDefinition = "tinyint(1) unsigned default 0 comment '是否其他传染病:1-是;0-否;'")
    private YesNo crowdInfection;

    // 失访状态
    @Column(name = "follow_state", columnDefinition = "tinyint(2) unsigned default 1 comment '失访状态:1-正常;2-暂时失访;3-永久失访'")
    private FollowState followState;

    // 失访原因
    @Column(name = "lost_follow_reason", columnDefinition = "tinyint(2) unsigned default 0 comment '失访原因:1-外出打工;2-上班/下地劳作;3-外出求学;4-本地上学;5-走失;6-拒访;7-求医;8-连续3次未到访;9-死亡;10-迁出;11-其他;'")
    private LostFollowReason lostFollowReason;

    // 签约相关
    @Column(name = "sign_state", columnDefinition = "tinyint(2) unsigned default 0 comment '签约状态:0-未签约;1-已签约;'")
    private SignState signState;

    @Column(name = "sign_expired_at", columnDefinition = "date NULL comment '签约到期日期'")
    private Date signExpiredAt;

    public void setCrowds(List<CrowdType> crowds) {
        this.setCrowdHypertension(YesNo.NO);
        this.setCrowdDiabetes2(YesNo.NO);
        this.setCrowdMentalDisorder(YesNo.NO);
        this.setCrowdPulmonaryTuberculosis(YesNo.NO);
        this.setCrowdMaternal(YesNo.NO);
        this.setCrowdElder(YesNo.NO);
        this.setCrowdChild(YesNo.NO);
        this.setCrowdPoor(YesNo.NO);
        this.setCrowdDisabled(YesNo.NO);
        this.setCrowdInfection(YesNo.NO);

        for (CrowdType crowd : crowds) {
            if (CrowdType.HYPERTENSION.equals(crowd)) {
                this.setCrowdHypertension(YesNo.YES);
            }
            if (CrowdType.DIABETES_II.equals(crowd)) {
                this.setCrowdDiabetes2(YesNo.YES);
            }
            if (CrowdType.MENTAL_DISORDER.equals(crowd)) {
                this.setCrowdMentalDisorder(YesNo.YES);
            }
            if (CrowdType.PULMONARY_TUBERCULOSIS.equals(crowd)) {
                this.setCrowdPulmonaryTuberculosis(YesNo.YES);
            }
            if (CrowdType.MATERNAL.equals(crowd)) {
                this.setCrowdMaternal(YesNo.YES);
            }
            if (CrowdType.ELDER.equals(crowd)) {
                this.setCrowdElder(YesNo.YES);
            }
            if (CrowdType.CHILD.equals(crowd)) {
                this.setCrowdChild(YesNo.YES);
            }
            if (CrowdType.POOR.equals(crowd)) {
                this.setCrowdPoor(YesNo.YES);
            }
            if (CrowdType.DISABLED.equals(crowd)) {
                this.setCrowdDisabled(YesNo.YES);
            }
            if (CrowdType.INFECTION.equals(crowd)) {
                this.setCrowdInfection(YesNo.YES);
            }
        }
    }

    public List<CrowdType> getCrowds() {
        List<CrowdType> types = Lists.newArrayList();

        if (YesNo.YES.equals(this.getCrowdHypertension())) {
            types.add(CrowdType.HYPERTENSION);
        }
        if (YesNo.YES.equals(this.getCrowdDiabetes2())) {
            types.add(CrowdType.DIABETES_II);
        }
        if (YesNo.YES.equals(this.getCrowdMentalDisorder())) {
            types.add(CrowdType.MENTAL_DISORDER);
        }
        if (YesNo.YES.equals(this.getCrowdPulmonaryTuberculosis())) {
            types.add(CrowdType.PULMONARY_TUBERCULOSIS);
        }
        if (YesNo.YES.equals(this.getCrowdMaternal())) {
            types.add(CrowdType.MATERNAL);
        }
        if (YesNo.YES.equals(this.getCrowdElder())) {
            types.add(CrowdType.ELDER);
        }
        if (YesNo.YES.equals(this.getCrowdChild())) {
            types.add(CrowdType.CHILD);
        }
        if (YesNo.YES.equals(this.getCrowdPoor())) {
            types.add(CrowdType.POOR);
        }
        if (YesNo.YES.equals(this.getCrowdDisabled())) {
            types.add(CrowdType.DISABLED);
        }
        if (YesNo.YES.equals(this.getCrowdInfection())) {
            types.add(CrowdType.INFECTION);
        }
        return types;
    }


    /**
     * 得到签约状态的计算值(不包含暂停服务)
     *
     * @return 状态值
     */
    public SignState getComputedSignState() {
        if (signState != null && signState.isSigned() && signExpiredAt != null) {
            IntInstant days30Before = IntInstant.of(signExpiredAt).addDays(-30);
            IntInstant now = IntInstant.now();
            if (now.getDate() >= days30Before.getDate()) {
                // 即将到期
                return SignState.DUE_TO_EXPIRE;
            }
        }
        return signState == null ? SignState.UNSIGNED : signState;
    }

    /**
     * 统计该居民是否普通人群标签
     *
     * @return 是否
     */
    public boolean isCrowdNormal() {
        BeanWrapper citizenWrapper = new BeanWrapperImpl(this);
        // 打了人群标签的属性数量
        Optional<YesNo> isTagged = Arrays.stream(CrowdType.values())
                // 获取属性
                .map(type -> (YesNo) citizenWrapper.getPropertyValue(type.getFieldName()))
                .filter(Objects::nonNull)
                // 只要查找到一个标记为YES的，都不是普通人群
                .filter(YesNo::getBoolean)
                .findFirst();
        // 没有标记，为普通人群 否则 为重点人群
        return !isTagged.isPresent();
    }

}
