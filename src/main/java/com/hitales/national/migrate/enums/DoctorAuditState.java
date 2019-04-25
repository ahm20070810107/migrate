package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 医生资料审核状态
 *
 * @author harryhe
 */
@EnumTag(key = "doctor_audit_state", name = "医生账户(资料）审核状态")
public enum DoctorAuditState implements IntEnum, Describable {
    /**
     * 待审核
     */
    SUBMITTED(1, "待审核"),
    /**
     * 审核通过
     */
    APPROVED(2, "审核通过"),
    /**
     * 审核拒绝（拒绝7天内可以重复提交）
     */
    DENIED(3, "驳回"),
    ;
    private Integer key;
    private String desc;

    DoctorAuditState(Integer key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getDesc() {
        return desc;
    }


    /**
     * 当前是否是审核拒绝状态
     *
     * @return 是否是审核拒绝状态
     */
    public boolean isDenied() {
        return DENIED.equals(this);
    }

    /**
     * 当前是否是审核通过状态
     *
     * @return 是否
     */
    public boolean isApproved() {
        return APPROVED.equals(this);
    }
}