package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 医疗费用支付方式
 *
 * @author harryhe
 */
@EnumTag(key = "medical_payment", name = "医疗费用支付方式")
public enum MedicalPayment implements IntEnum, Describable {

	/**
	 * 城镇职工基本医疗保险
	 */
	INSURANCE_FOR_URBAN_EMPLOYEES(1, "城镇职工基本医疗保险"),
	/**
	 * 城镇居民基本医疗保险
	 */
	INSURANCE_FOR_URBAN_RESIDENTS(2, "城镇居民基本医疗保险"),
	/**
	 * 新型农村合作医疗
	 */
	NEW_RURAL_COOPERATIVE(3, "新型农村合作医疗"),
	/**
	 * 贫困救助
	 */
	POVERTY_RELIEF(4, "贫困救助"),
	/**
	 * 商业医疗保险
	 */
	COMMERCIAL_MEDICAL_INSURANCE(5, "商业医疗保险"),
	/**
	 * 全公费
	 */
	SOCIALIZED_MEDICINE(6, "全公费"),
	/**
	 * 全自费
	 */
	SELF_PAYING(7, "全自费"),
	/**
	 * 其他
	 */
	OTHER(8, "其他"),;
	private Integer key;
	private String desc;

	private MedicalPayment(Integer key, String desc) {
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