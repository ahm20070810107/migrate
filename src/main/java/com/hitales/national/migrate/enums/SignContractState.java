package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 合约有效性状态
 *
 * @author harryhe
 */
@EnumTag(key = "sign_contract_state", name = "合约有效性状态")
public enum SignContractState implements IntEnum, Describable {

	/**
	 * 有效合约
	 */
	AVAILABLE(1, "有效"),
	/**
	 * 废弃合约:因为有新的合约导致本合约被废弃，比如续约
	 */
	ABANDONED(2, "废弃"),
	/**
	 * 自然结束：自然到期终止
	 */
	COMPLETED(3, "结束"),
	/**
	 * 提前中止：该合约被提前中止
	 */
	TERMINATED(4, "中止"),;
	private Integer key;
	private String desc;

	SignContractState(Integer key, String desc) {
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