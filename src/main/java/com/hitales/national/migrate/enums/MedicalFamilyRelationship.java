package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 家族史中家庭关系类型
 *
 * @author harryhe
 */
@EnumTag(key = "medical_family_relationship", name = "健康档案：家族史中家庭关系类型")
public enum MedicalFamilyRelationship implements IntEnum, Describable {
	/**
	 * 父亲
	 */
	FATHER(1, "父亲"),
	/**
	 * 母亲
	 */
	MOTHER(2, "母亲"),
	/**
	 * 兄弟姐妹
	 */
	SIBLING(3, "兄弟姐妹"),
	/**
	 * 子女
	 */
	CHILDREN(4, "子女"),;
	private Integer key;
	private String desc;

	private MedicalFamilyRelationship(Integer key, String desc) {
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