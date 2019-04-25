package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 婚姻状况
 *
 * @author harryhe
 */
@EnumTag(key = "marital_state", name = "婚姻状况")
public enum MaritalState implements IntEnum, Describable {
	/**
	 * 未婚
	 */
	UNMARRIED(1, "未婚"),
	/**
	 * 已婚
	 */
	MARRIED(2, "已婚"),
	/**
	 * 丧偶
	 */
	WIDOWED(3, "丧偶"),
	/**
	 * 离婚
	 */
	DIVORCE(4, "离婚"),
	/**
	 * 不详
	 */
	NOT_MENTIONED(5, "不详");
	private Integer key;
	private String desc;

	private MaritalState(Integer key, String desc) {
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