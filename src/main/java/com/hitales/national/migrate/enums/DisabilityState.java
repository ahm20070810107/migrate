package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民残疾情况
 *
 * @author harryhe
 */
@EnumTag(key = "disability_state", name = "居民残疾情况")
public enum DisabilityState implements IntEnum, Describable {

	/**
	 * 视力残疾
	 */
	VISION(1, "视力残疾"),
	/**
	 * 听力残疾
	 */
	HEARING(2, "听力残疾"),
	/**
	 * 言语残疾
	 */
	LANGUAGE(3, "言语残疾"),
	/**
	 * 肢体残疾
	 */
	LIMBS(4, "肢体残疾"),
	/**
	 * 智力残疾
	 */
	INTELLIGENCE(5, "智力残疾"),
	/**
	 * 精神残疾
	 */
	MIND(6, "精神残疾"),
	/**
	 * 其他残疾
	 */
	OTHER(7, "其他"),;
	private Integer key;
	private String desc;

	private DisabilityState(Integer key, String desc) {
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