package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 血型: ABO
 *
 * @author harryhe
 */
@EnumTag(key = "blood_type_abo", name = "血型: ABO")
public enum BloodTypeABO implements IntEnum, Describable {
	/**
	 * A型血
	 */
	TYPE_A(1, "A型"),
	/**
	 * B型血
	 */
	TYPE_B(2, "B型"),
	/**
	 * O型血
	 */
	TYPE_O(3, "O型"),
	/**
	 * AB型血
	 */
	TYPE_AB(4, "AB型"),
	/**
	 * 不详
	 */
	TYPE_UNKNOWN(5, "不详"),;
	private Integer key;
	private String desc;

	private BloodTypeABO(Integer key, String desc) {
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