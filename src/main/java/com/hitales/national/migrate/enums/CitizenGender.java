package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民性别
 *
 * @author harryhe
 */
@EnumTag(key = "citizen_gender", name = "居民性别")
public enum CitizenGender implements IntEnum, Describable {

	/**
	 *
	 */
	UNKNOWN(0, "未知"),
	/**
	 * 男
	 */
	MALE(1, "男"),
	/**
	 * 女
	 */
	FEMALE(2, "女"),
	/**
	 * 未说明
	 */
	NOT_SPECIFIED(9, "未说明");
	private Integer key;
	private String desc;

	private CitizenGender(Integer key, String desc) {
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