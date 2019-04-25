package com.hitales.national.migrate.enums;

import com.google.common.collect.Sets;
import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

import java.util.Set;

/**
 * 医生账户可用性状态
 *
 * @author harryhe
 */
@EnumTag(key = "doctor_account_state", name = "医生账户可用性状态")
public enum DoctorAccountState implements IntEnum, Describable {
	/**
	 * 活跃
	 */
	AVAILABLE(1, "启用"),
	/**
	 * 锁定（临时停用）
	 */
	LOCKED(2, "临时停用"),
	/**
	 * 账户（永久停用）
	 */
	CANCELLED(3, "停用"),;
	private Integer key;
	private String desc;

	DoctorAccountState(Integer key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	/**
	 * 返回 非活跃状态的集合
	 *
	 * @return 非活跃状态集合
	 */
	public static Set<DoctorAccountState> getInactiveSets() {
		return Sets.newHashSet(LOCKED, CANCELLED);
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
	 * 是否是启用状态
	 *
	 * @return 是否
	 */
	public boolean isAvailable() {
		return this.equals(AVAILABLE);
	}

	/**
	 * 是否 临时停用
	 *
	 * @return 是否
	 */
	public boolean isLocked() {
		return this.equals(LOCKED);
	}

	/**
	 * 是否 永久停用
	 *
	 * @return 是否
	 */
	public boolean isCancelled() {
		return this.equals(CANCELLED);
	}
}