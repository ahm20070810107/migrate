package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 暴露史
 *
 * @author jingang
 */
@EnumTag(key = "exposure_type", name = "暴露史")
public enum ExposureType implements IntEnum, Describable {

	/**
	 * 化学品
	 */
	CHEMICALS(1, "化学品"),
	/**
	 * 毒物
	 */
	POISON(2, "毒物"),
	/**
	 * 射线
	 */
	RADIAL(3, "射线"),;

	private Integer key;
	private String desc;

	private ExposureType(Integer key, String desc) {
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