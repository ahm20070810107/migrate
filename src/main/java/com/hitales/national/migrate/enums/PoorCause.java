package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民致贫原因
 *
 * @author harryhe
 */
@EnumTag(key = "poor_cause", name = "居民致贫原因")
public enum PoorCause implements IntEnum, Describable {


	/**
	 * 因病
	 */
	DISEASE(1, "因病"),
	/**
	 * 因残
	 */
	DISABILITY(2, "因残"),
	/**
	 * 因学
	 */
	STUDY(3, "因学"),
	/**
	 * 因灾
	 */
	DISASTER(4, "因灾"),
	/**
	 * 因婚
	 */
	MARRIAGE(5, "因婚"),
	/**
	 * 缺土地
	 */
	LACK_OF_LAND(6, "缺土地"),
	/**
	 * 缺水
	 */
	LACK_OF_WATER(7, "缺水"),
	/**
	 * 缺技术
	 */
	LACK_OF_SKILL(8, "缺技术"),
	/**
	 * 缺劳力
	 */
	LACK_OF_LABOR(9, "缺劳力"),
	/**
	 * 缺资金
	 */
	LACK_OF_FUNDS(10, "缺资金"),
	/**
	 * 交通条件落后
	 */
	LACK_OF_TRAFFIC(11, "交通条件落后"),
	/**
	 * 自身发展动力不足
	 */
	LACK_OF_MOTIVE_FORCE(12, "自身发展动力不足"),
	/**
	 * 其他
	 */
	OTHER(13, "其他"),;
	private Integer key;
	private String desc;

	private PoorCause(Integer key, String desc) {
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