package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 文化程度
 *
 * @author harryhe
 */
@EnumTag(key = "education_degree", name = "文化程度")
public enum EducationDegree implements IntEnum, Describable {
	/**
	 * 研究生
	 */
	POST_GRADUATE(1, "研究生"),
	/**
	 * 本科
	 */
	UNDER_GRADUATE(2, "大学本科"),
	/**
	 * 大学专科和专科学校
	 */
	JUNIOR_COLLEGE_EDUCATION(3, "大学专科和专科学校"),
	/**
	 * 中等专业学校
	 */
	SPECIAL_SECONDARY_SCHOOL(4, "中等专业学校"),
	/**
	 * 技工学校
	 */
	VESTIBULE_SCHOOL(5, "技工学校"),
	/**
	 * 高中
	 */
	SENIOR_MIDDLE_SCHOOL(6, "高中"),
	/**
	 * 初中
	 */
	JUNIOR_MIDDLE_SCHOOL(7, "初中"),
	/**
	 * 小学
	 */
	PRIMARY_SCHOOL(8, "小学"),
	/**
	 * 文盲或半文盲
	 */
	ILLITERACY(9, "文盲或半文盲"),
	/**
	 * 不详
	 */
	UNKNOWN(10, "不详");
	private Integer key;
	private String desc;

	private EducationDegree(Integer key, String desc) {
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