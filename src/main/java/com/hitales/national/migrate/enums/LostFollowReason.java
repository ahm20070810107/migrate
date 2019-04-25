package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 失访原因
 *
 * @author jingang
 */
@EnumTag(key = "lost_follow_reason", name = "居民失访原因")
public enum LostFollowReason implements IntEnum, Describable {

	/**
	 * 外出打工
	 */
	OUT_WORK(1, "外出打工"),
	/**
	 * 上班/下地劳作
	 */
	WORK(2, "上班/下地劳作"),
	/**
	 * 外出求学
	 */
	OUT_STUDY(3, "外出求学"),
	/**
	 * 本地上学
	 */
	STUDY(4, "本地上学"),
	/**
	 * 走失
	 */
	LOST(5, "走失"),
	/**
	 * 拒访
	 */
	REFUSE(6, "拒访"),
	/**
	 * 求医
	 */
	SEEK_MEDICAL_TREATMENT(7, "求医"),
	/**
	 * 连续3次未到访
	 */
	MISS_3_TIMES(8, "连续3次未到访"),
	/**
	 * 死亡
	 */
	DEAD(9, "死亡"),
	/**
	 * 迁出
	 */
	MOVE_OUT(10, "迁出"),
	/**
	 * 其他
	 */
	OTHER(11, "其他"),;
	private Integer key;
	private String desc;

	private LostFollowReason(Integer key, String desc) {
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