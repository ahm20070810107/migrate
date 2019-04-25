package com.hitales.national.migrate.enums;

import com.hitales.commons.enums.tag.EnumTag;
import com.hitales.commons.enums.typeable.Describable;
import com.hitales.commons.enums.typeable.IntEnum;

/**
 * 民族
 *
 * @author harryhe
 */
@EnumTag(key = "nation", name = "民族", description = "GB 3304-1991 中国各民族名称的罗马字母拼写法和代码")
public enum Nation implements IntEnum, Describable {
	/**
	 * 汉族
	 */
	HAN(1, "汉族"),
	/**
	 * 蒙古族
	 */
	MENG_GU(2, "蒙古族"),

	/**
	 * 回族
	 */
	HUI(3, "回族"),
	/**
	 * 藏族
	 */
	ZANG(4, "藏族"),
	/**
	 * 维吾尔族
	 */
	WEI_WU_ER(5, "维吾尔族"),
	/**
	 * 苗族
	 */
	MIAO(6, "苗族"),
	/**
	 * 彝族
	 */
	YI(7, "彝族"),
	/**
	 * 壮族
	 */
	ZHUANG(8, "壮族"),
	/**
	 * 布依族
	 */
	BU_YI(9, "布依族"),
	/**
	 * 朝鲜族
	 */
	CHAO_XIAN(10, "朝鲜族"),
	/**
	 * 满族
	 */
	MAN(11, "满族"),
	/**
	 * 侗族
	 */
	DONG(12, "侗族"),
	/**
	 * 瑶族
	 */
	YAO(13, "瑶族"),
	/**
	 * 白族
	 */
	BAI(14, "白族"),
	/**
	 * 土家族
	 */
	TU_JIA(15, "土家族"),
	/**
	 * 哈尼族
	 */
	HA_NI(16, "哈尼族"),
	/**
	 * 傣族
	 */
	DAI(18, "傣族"),
	/**
	 * 哈萨克族
	 */
	LI(19, "黎族"),
	/**
	 * 傈僳族
	 */
	LI_LI(20, "傈僳族"),
	/**
	 * 佤族
	 */
	WA(21, "佤族"),
	/**
	 * 畲族
	 */
	SHE(22, "畲族"),
	/**
	 * 高山族
	 */
	GAO_SHAN(23, "高山族"),
	/**
	 * 拉祜族
	 */
	LA_GU(24, "拉祜族"),
	/**
	 * 水族
	 */
	SHUI(25, "水族"),
	/**
	 * 东乡族
	 */
	DONG_XIANG(26, "东乡族"),
	/**
	 * 纳西族
	 */
	NA_XI(27, "纳西族"),
	/**
	 * 景颇族
	 */
	JING_BO(28, "景颇族"),
	/**
	 * 柯尔克孜族
	 */
	KE_ER_KE_ZI(29, "柯尔克孜族"),
	/**
	 * 土族
	 */
	TU(30, "土族"),
	/**
	 * 仫佬族
	 */
	MU_LAO(32, "仫佬族"),
	/**
	 * 羌族
	 */
	QIANG(33, "羌族"),
	/**
	 * 布朗族
	 */
	BU_LANG(34, "布朗族"),
	/**
	 * 撒拉族
	 */
	SA_LA(35, "撒拉族"),
	/**
	 * 毛难族
	 */
	MAO_NAN(36, "毛难族"),
	/**
	 * 仡佬族
	 */
	QI_LAO(37, "仡佬族"),
	/**
	 * 锡伯族
	 */
	XI_BO(38, "锡伯族"),
	/**
	 * 阿昌族
	 */
	A_CHANG(39, "阿昌族"),
	/**
	 * 普米族
	 */
	PU_MI_ZU(40, "普米族"),
	/**
	 * 塔吉克族
	 */
	TA_JI_KE(41, "塔吉克族"),
	/**
	 * 怒族
	 */
	NU(42, "怒族"),
	/**
	 * 俄罗斯族
	 */
	E_LUO_SI(44, "俄罗斯族"),
	/**
	 * 鄂温克族
	 */
	E_WEN_KE(45, "鄂温克族"),
	/**
	 * 崩龙族(德昂族)
	 */
	DE_ANG(46, "崩龙族"),
	/**
	 * 保安族
	 */
	BAO_AN(47, "保安族"),
	/**
	 * 裕固族
	 */
	YU_GU(48, "裕固族"),
	/**
	 * 京族
	 */
	JING(49, "京族"),
	/**
	 * 塔塔尔族
	 */
	TA_TA_ER(50, "塔塔尔族"),
	/**
	 * 独龙族
	 */
	DU_LONG(51, "独龙族"),
	/**
	 * 鄂伦春族
	 */
	E_LUN_CHUN(52, "鄂伦春族"),
	/**
	 * 赫哲族
	 */
	HE_ZHE(53, "赫哲族"),
	/**
	 * 门巴族
	 */
	MEN_BA(54, "门巴族"),
	/**
	 * 珞巴族
	 */
	LUO_BA(55, "珞巴族"),
	/**
	 * 基诺族
	 */
	JI_NUO(56, "基诺族"),
	/**
	 * 其他
	 */
	OTHER(97, "其他"),
	/**
	 * 外国血统
	 */
	FOREIGNER(98, "外国血统"),;
	private Integer key;
	private String desc;

	private Nation(Integer key, String desc) {
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