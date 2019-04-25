package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民人群分类
 *
 * @author harryhe
 */
@EnumTag(key = "crowd_type", name = "居民人群分类")
public enum CrowdType implements IntEnum, Describable {
	/**
	 * 高血压患者
	 */
	HYPERTENSION(1, "高血压患者", "高", "crowdHypertension"),
	/**
	 * 2型糖尿病患者
	 */
	DIABETES_II(2, "2型糖尿病患者", "糖", "crowdDiabetes2"),
	/**
	 * 严重精神障碍患者
	 */
	MENTAL_DISORDER(3, "严重精神障碍患者", "精", "crowdMentalDisorder"),
	/**
	 * 肺结核患者
	 */
	PULMONARY_TUBERCULOSIS(4, "肺结核患者", "结", "crowdPulmonaryTuberculosis"),
	/**
	 * 孕产妇
	 */
	MATERNAL(5, "孕产妇", "孕", "crowdMaternal"),
	/**
	 * 65岁及以上老年人
	 */
	ELDER(6, "65岁及以上老年人", "老", "crowdElder"),
	/**
	 * 0-6岁儿童
	 */
	CHILD(7, "0-6岁儿童", "幼", "crowdChild"),
	/**
	 * 贫困户
	 */
	POOR(8, "贫困户", "贫", "crowdPoor"),
	/**
	 * 残疾人
	 */
	DISABLED(9, "残疾人", "残", "crowdDisabled"),
	/**
	 * 其他传染病
	 */
	INFECTION(10, "其他传染病", "传", "crowdInfection");
	private Integer key;
	private String desc;
	private String shortDesc;
	private String fieldName;

	CrowdType(Integer key, String desc, String shortDesc, String fieldName) {
		this.key = key;
		this.desc = desc;
		this.shortDesc = shortDesc;
		this.fieldName = fieldName;
	}

	@Override
	public Integer getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public String getFieldName() {
		return fieldName;
	}

}