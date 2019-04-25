package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 签约事件
 *
 * @author harryhe
 */
@EnumTag(key = "sign_event", name = "签约事件")
public enum SignEvent implements IntEnum, Describable {

	/**
	 * 签约
	 */
	SIGN(1, "签约"),
	/**
	 * 续约
	 */
	RENEW(2, "签约"),
	/**
	 * （医生）解约
	 */
	TERMINATED_BY_DOCTOR(3, "解约"),

	/**
	 * 到期解约
	 */
	TERMINATED_BY_SYSTEM(4, "合约终止"),

	/**
	 *
	 */
	DUE_TO_EXPIRED(5, "即将到期"),

	/**
	 * （医生更换医疗机构）解约
	 */
	TERMINATED_BY_CLINIC(6, "解约"),

	/**
	 * 居民更换所在地导致的解约
	 */
	TERMINATED_BY_CITIZEN_LOCATION(7, "解约"),

	/**
	 * 医生账号处于临时停用
	 */
	PAUSE_BY_LOCK(8, "暂停"),
	/**
	 * 医生账号处于停用
	 */
	PAUSE_BY_CANCEL(9, "暂停"),
	/**
	 * 启用导致签约恢复正常
	 */
	RECOVER_BY_ACTIVE(10, "签约"),
	/**
	 * 注册导致签约恢复正常
	 */
	RECOVER_BY_REGISTER(11, "签约"),

	/**
	 * 居民永久失访
	 */
	TERMINATED_BY_DEAD(12, "合约终止"),

	/**
	 * 居民迁出永久失访
	 */
	TERMINATED_BY_OUT(13, "合约终止"),;
	private Integer key;
	private String desc;

	SignEvent(Integer key, String desc) {
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