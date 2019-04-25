package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民资料审核状态
 *
 * @author harryhe
 */
@EnumTag(key = "citizen_audit_state", name = "居民资料审核状态")
public enum CitizenAuditState implements IntEnum, Describable {
	/**
	 * 待审核
	 */
	SUBMITTED(1, "待审核"),
	/**
	 * 审核通过
	 */
	APPROVED(2, "审核通过"),

	/**
	 * 审核拒绝
	 */
	DENIED(3, "审核拒绝"),;
	private Integer key;
	private String desc;

	private CitizenAuditState(Integer key, String desc) {
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