package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 血型: RH
 *
 * @author harryhe
 */
@EnumTag(key = "blood_type_rh", name = "血型: RH分类")
public enum BloodTypeRH implements IntEnum, Describable {
	/**
	 * 阴性
	 */
	NEGATIVE(1, "阴性"),
	/**
	 * 阳性
	 */
	POSITIVE(2, "阳性"),
	/**
	 * 不详
	 */
	TYPE_UNKNOWN(3, "不详"),;
	private Integer key;
	private String desc;

	private BloodTypeRH(Integer key, String desc) {
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