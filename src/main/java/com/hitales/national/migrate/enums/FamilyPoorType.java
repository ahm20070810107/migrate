package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.YesNo;
import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 贫困户类型
 *
 * @author harryhe
 */
@EnumTag(key = "pool_family_state", name = "贫困户类型")
public enum FamilyPoorType implements IntEnum, Describable {
	/**
	 * 贫困户
	 */
	POOR(1, "贫困户"),
	/**
	 * 非贫困户
	 */
	NON_POOR(2, "非贫困户"),;
	private Integer key;
	private String desc;

	private FamilyPoorType(Integer key, String desc) {
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
	 * 返回是否贫困户
	 *
	 * @return 是否枚举
	 */
	public YesNo isPoor() {
		switch (this) {
			case POOR:
				return YesNo.YES;
			default:
				return YesNo.NO;
		}
	}

}