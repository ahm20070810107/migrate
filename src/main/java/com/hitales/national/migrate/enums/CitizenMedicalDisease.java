package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 既往疾病史疾病类型
 *
 * @author harryhe
 */
@EnumTag(key = "citizen_medical_disease", name = "健康档案：既往疾病史疾病类型")
public enum CitizenMedicalDisease implements IntEnum, Describable {

	/**
	 * 高血压
	 */
	HYPERTENSION(2, "高血压"),
	/**
	 * 糖尿病
	 */
	DIABETES(3, "糖尿病"),
	/**
	 * 冠心病
	 */
	CORONARY_DISEASE(4, "冠心病"),

	/**
	 * 慢性阻塞性肺疾病
	 */
	CHRONIC_OBSTRUCTIVE_PULMONARY(5, "慢性阻塞性肺疾病"),

	/**
	 * 恶性肿瘤
	 */
	MALIGNANT_TUMOR(6, "恶性肿瘤"),
	/**
	 * 脑卒中
	 */
	CEREBRAL_APOPLEXY(7, "脑卒中"),

	/**
	 * 严重精神障碍
	 */
	MENTAL_DISORDER(8, "严重精神障碍"),

	/**
	 * 结核病
	 */
	TUBERCULOSIS(9, "结核病"),
	/**
	 * 肝炎
	 */
	HEPATITIS(10, "肝炎"),
	/**
	 * 其他法定传染病
	 */
	OTHER_INFECTIOUS_DISEASE(11, "其他法定传染病"),
	/**
	 * 职业病
	 */
	OCCUPATIONAL_DISEASE(12, "职业病"),
	;
	private Integer key;
	private String desc;

	private CitizenMedicalDisease(Integer key, String desc) {
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