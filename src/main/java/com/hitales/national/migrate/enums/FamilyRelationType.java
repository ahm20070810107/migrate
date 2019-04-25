package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.YesNo;
import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 家庭成员类型
 *
 * @author harryhe
 */
@EnumTag(key = "family_relation_type", name = "家庭成员类型")
public enum FamilyRelationType implements IntEnum, Describable {

	/**
	 * 户主
	 */
	户主(1, "户主"),
	/**
	 * 户主配偶
	 */
	配偶(2, "配偶"),
	/**
	 * 户主子女
	 */
	子女(3, "子女"),
	/**
	 * 户主的配偶
	 */
	孙女(4, "孙女"),
	/**
	 * 户主父母
	 */
	父母(5, "父母"),
	/**
	 * 户主祖父母
	 */
	祖父母(6, "祖父母"),
	/**
	 * 户主外祖父母
	 */
	外祖父母(7, "外祖父母"),
	/**
	 * 户主兄弟
	 */
	兄弟(8, "兄弟"),
	/**
	 * 户主姊妹
	 */
	姊妹(9, "姊妹"),
	/**
	 * 户主旁系亲属
	 */
	旁系亲属(10, "旁系亲属"),
	/**
	 * 户主的其他亲属
	 */
	其他亲属(11, "其他亲属"),
	/**
	 * 其他
	 */
	其他(12, "其他"),;
	private Integer key;
	private String desc;

	private FamilyRelationType(Integer key, String desc) {
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
	 * 当前关系是否为户主
	 *
	 * @return 是否
	 */
	public YesNo isOwner() {
		return YesNo.booleanOf(户主.equals(this));
	}
}