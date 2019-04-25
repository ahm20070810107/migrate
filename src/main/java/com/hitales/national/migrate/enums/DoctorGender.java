package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 医生性别
 *
 * @author harryhe
 */
@EnumTag(key = "doctor_gender", name = "医生性别")
public enum DoctorGender implements IntEnum, Describable {

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

	private DoctorGender(Integer key, String desc) {
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