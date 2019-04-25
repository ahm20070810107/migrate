package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民访问跟踪状态(失访状态）
 *
 * @author harryhe
 */
@EnumTag(key = "follow_state", name = "居民访问跟踪状态(失访状态）")
public enum FollowState implements IntEnum, Describable {


	/**
	 * 正常
	 */
	FOLLOW(1, "正常"),
	/**
	 * 暂时失访
	 */
	MISS_TEMPORAL(2, "暂时失访"),
	/**
	 * 永久失访
	 */
	MISS_FOREVER(3, "永久失访"),;
	private Integer key;
	private String desc;

	private FollowState(Integer key, String desc) {
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
	 * 当前状态是否是 正常状态
	 *
	 * @return 是否
	 */
	public boolean isFollow() {
		return this.equals(FOLLOW);
	}

	/**
	 * 当前状态是否是 暂时失访
	 *
	 * @return 是否
	 */
	public boolean isMissTemporal() {
		return this.equals(MISS_TEMPORAL);
	}

	/**
	 * 当前状态是否是 永久失访
	 *
	 * @return 是否
	 */
	public boolean isMissForever() {
		return this.equals(MISS_FOREVER);
	}

}
