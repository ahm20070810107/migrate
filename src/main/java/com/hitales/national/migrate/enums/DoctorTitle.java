package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 医生职称
 *
 * @author harryhe
 */
@EnumTag(key = "doctor_title", name = "医生职称")
public enum DoctorTitle implements IntEnum, Describable {
	/**
	 * 主任医师
	 */
	主任医师(1, "主任医师"),
	/**
	 * 副主任医师
	 */
	副主任医师(2, "副主任医师"),

	/**
	 * 主治医师
	 */
	主治医师(3, "主治医师"),
	/**
	 * 医师
	 */
	医师(4, "医师"),
	/**
	 * 助理医师
	 */
	助理医师(5, "助理医师"),
	/**
	 * 主任护师
	 */
	主任护师(6, "主任护师"),
	/**
	 * 副主任护师
	 */
	副主任护师(7, "副主任护师"),
	/**
	 * 主管护师
	 */
	主管护师(8, "主管护师"),
	/**
	 * 护师
	 */
	护师(9, "护师"),
	/**
	 * 主治医师
	 */
	护士(10, "护士"),
	/**
	 * 主治医师
	 */
	其他(11, "其他"),;
	private Integer key;
	private String desc;

	private DoctorTitle(Integer key, String desc) {
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