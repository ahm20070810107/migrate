package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 常住类型
 *
 * @author harryhe
 */
@EnumTag(key = "resident_type", name = "常住类型", description = "是否当地户籍")
public enum ResidentType implements IntEnum, Describable {
	/**
	 * 户籍
	 */
	REGISTERED(1, "户籍"),
	/**
	 * 非户籍
	 */
	NON_REGISTERED(2, "非户籍"),;
	private Integer key;
	private String desc;

	private ResidentType(Integer key, String desc) {
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