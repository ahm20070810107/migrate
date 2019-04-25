package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 签约解除对象类型
 *
 * @author harryhe
 */
@EnumTag(key = "sign_terminated_by_type", name = "签约解除对象类型")
public enum SignTerminatedByType implements IntEnum, Describable {
	/**
	 * 系统：由系统触发的解约
	 */
	SYSTEM(1, "系统"),
	/**
	 * 医生：由医生触发的解约
	 */
	DOCTOR(2, "医生"),
//    /**
//     * 居民：由居民触发的解约
//     */
//    CITIZEN(3, "居民"),
	;

	private Integer key;
	private String desc;

	private SignTerminatedByType(Integer key, String desc) {
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

}