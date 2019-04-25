package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 药物过敏史
 *
 * @author jingang
 */
@EnumTag(key = "drug_allergy_type", name = "药物过敏史")
public enum DrugAllergyType implements IntEnum, Describable {

	/**
	 * 青霉素
	 */
	PENICILLIN(1, "青霉素"),
	/**
	 * 磺胺
	 */
	SULFONAMIDE(2, "磺胺"),
	/**
	 * 链霉素
	 */
	STREPTOMYCIN(3, "链霉素"),
	/**
	 * 其他
	 */
	OTHER(4, "其他"),;

	private Integer key;
	private String desc;

	private DrugAllergyType(Integer key, String desc) {
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