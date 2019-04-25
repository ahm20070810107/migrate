package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

import java.util.Optional;

/**
 * 医生角色
 *
 * @author harryhe
 */
@EnumTag(key = "doctor_role", name = "医生角色")
public enum DoctorRole implements IntEnum, Describable {

	/**
	 * 村医
	 */
	VILLAGE(1, "村医"),
	/**
	 * 乡医
	 */
	TOWN(2, "乡医"),
	/**
	 * 专家
	 */
	EXPERT(3, "专家"),
	/**
	 * 卫生院院长
	 */
	DIRECTOR(4, "院长"),
	/**
	 * 卫计局管理员
	 */
	COUNTY_MANAGER(5, "卫计局管理员"),;
	private Integer key;
	private String desc;

	DoctorRole(Integer key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	/**
	 * 通过医疗机构深度映射角色
	 * // 对应关系: 深度 - 角色
	 * // 0 - 专家
	 * // 1 - 乡医
	 * // 2 - 村医
	 *
	 * @param depth 深度
	 * @return 医生角色
	 */
	public static DoctorRole mapByClinicDepth(Integer depth) {
		return Optional.ofNullable(depth)
				   .map(dep -> {
					   switch (dep) {
						   case 0:
							   return DoctorRole.EXPERT;
						   case 1:
							   return DoctorRole.TOWN;
						   case 2:
							   return DoctorRole.VILLAGE;
						   default:
							   return null;
					   }
				   }).orElse(null);
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