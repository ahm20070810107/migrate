package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 签约状态
 *
 * @author harryhe
 */
@EnumTag(key = "sign_state", name = "签约状态")
public enum SignState implements IntEnum, Describable {
	/**
	 * 即将到期（计算状态）
	 */
	DUE_TO_EXPIRE(-1, "即将到期"),

	/**
	 * 暂停服务（计算状态）
	 */
	PAUSED(-2, "暂停服务"),

	/**
	 * 未签约
	 */
	UNSIGNED(0, "未签约"),
	/**
	 * 已签约
	 */
	SIGNED(1, "已签约"),;
	private Integer key;
	private String desc;

	SignState(Integer key, String desc) {
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
	 * 返回当前签约状态是否是"未签约"
	 *
	 * @return 是否是"未签约"
	 */
	public boolean isUnsigned() {
		return UNSIGNED.equals(this);
	}

	/**
	 * 返回当前签约状态是否是"签约"
	 *
	 * @return 是否是"已签约"
	 */
	public boolean isSigned() {
		return SIGNED.equals(this);
	}

	/**
	 * 返回当前签约状态是否是"暂停服务"
	 *
	 * @return 是否
	 */
	public boolean isPaused() {
		return PAUSED.equals(this);
	}

	/**
	 * 返回当前签约状态是否是"即将过期"
	 *
	 * @return 是否
	 */
	public boolean isDueToExpire() {
		return DUE_TO_EXPIRE.equals(this);
	}
}