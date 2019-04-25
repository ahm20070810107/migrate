package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 家庭成员变更方式
 *
 * @author harryhe
 */
@EnumTag(key = "family_member_change", name = " 家庭成员变更方式")
public enum FamilyMemberChange implements IntEnum, Describable {


	/**
	 * 添加
	 */
	ADD(1, "添加"),
	/**
	 * 退出
	 */
	DELETE(2, "退出"),;
	private Integer key;
	private String desc;

	private FamilyMemberChange(Integer key, String desc) {
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