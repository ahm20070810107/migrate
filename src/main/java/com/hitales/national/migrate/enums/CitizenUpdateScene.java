package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民更新场景
 *
 * @author jingang
 */
public enum CitizenUpdateScene implements IntEnum, Describable {
	COMPLETE_INFO(1, "新增时完善信息"),
	UPDATE(2, "更新"),;
	private Integer key;
	private String desc;

	private CitizenUpdateScene(Integer key, String desc) {
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