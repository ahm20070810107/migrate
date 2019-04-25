package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 居民身份状态
 *
 * @author harryhe
 */
@EnumTag(key = "id_state", name = "居民身份状态")
public enum IdState implements IntEnum, Describable {


	/**
	 * 有效身份
	 */
	AVAILABLE(1, "有效身份"),
	/**
	 * 临时身份
	 */
	TEMPORARY(2, "临时身份"),;
	private Integer key;
	private String desc;

	private IdState(Integer key, String desc) {
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
	 * 是否是临时身份
	 * @return 是否
	 */
	public boolean isTemporary(){
		return TEMPORARY.equals(this);
	}
}