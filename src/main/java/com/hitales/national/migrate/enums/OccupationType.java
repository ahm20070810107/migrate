package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民职业分类
 *
 * @author harryhe
 */
@EnumTag(key = "citizen_occupation_type", name = "职业分类")
public enum OccupationType implements IntEnum, Describable {
	/**
	 * 国家机关、党群组织、企业、事业单位负责人
	 */
	ID(0, "国家机关、党群组织、企业、事业单位负责人"),
	/**
	 * 专业技术人员
	 */
	SPECIALIST(1, "专业技术人员"),
	/**
	 * 办事人员和有关人员
	 */
	CLERICAL(2, "办事人员和有关人员"),
	/**
	 * 办事人员和有关人员
	 */
	BUSINESS(3, "商业、服务业人员"),
	/**
	 * 农、林、牧、渔、水利业生产人员
	 */
	PRODUCER(4, "农、林、牧、渔、水利业生产人员"),
	/**
	 * 生产、运输设备操作人员
	 */
	OPERATOR(5, "生产、运输设备操作人员"),
	/**
	 * 军人
	 */
	SOLDIER(6, "军人"),
	/**
	 * 不便分类的其他从业人员
	 */
	OTHER(7, "不便分类的其他从业人员"),
	/**
	 * 无职业
	 */
	NONE(8, "无职业"),;
	private Integer key;
	private String desc;

	private OccupationType(Integer key, String desc) {
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