package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 既往史类型
 *
 * @author harryhe
 */
@EnumTag(key = "medical_history_type", name = "健康档案：既往史类型")
public enum MedicalHistoryType implements IntEnum, Describable {
	/**
	 * 疾病史
	 */
	DISEASE(1, "疾病史"),
	/**
	 * 手术史
	 */
	OPERATION(2, "手术史"),
	/**
	 * 外伤史
	 */
	WOUND(3, "外伤史"),
	/**
	 * 输血史
	 */
	BLOOD_TRANS(4, "输血史"),;
	private Integer key;
	private String desc;

	private MedicalHistoryType(Integer key, String desc) {
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