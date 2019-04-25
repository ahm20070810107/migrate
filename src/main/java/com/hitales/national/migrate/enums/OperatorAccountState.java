package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 运营账户可用性状态
 *
 * @author harryhe
 */
@EnumTag(key = "operator_account_state", name = "运营账户可用性状态")
public enum OperatorAccountState implements IntEnum, Describable {
    /**
     * 活跃
     */
    AVAILABLE(1, "启用"),
    /**
     * 锁定（临时停用）
     */
    LOCKED(2, "临时停用"),



    ;
    private Integer key;
    private String desc;

    OperatorAccountState(Integer key, String desc) {
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
     * 是否是启用状态
     *
     * @return 是否
     */
    public boolean isAvailable() {
        return this.equals(AVAILABLE);
    }

    /**
     * 是否 临时停用
     *
     * @return 是否
     */
    public boolean isLocked() {
        return this.equals(LOCKED);
    }

}