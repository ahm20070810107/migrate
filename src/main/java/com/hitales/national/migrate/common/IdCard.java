package com.hitales.national.migrate.common;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;
import com.hitales.commons.util.ByteOpr;
import com.hitales.commons.util.IntInstant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class IdCard {

	/**
	 * 中国公民身份证号码最小长度。
	 */
	public static final int CHINA_ID_MIN_LENGTH = 15;

	/**
	 * 中国公民身份证号码最大长度。
	 */
	public static final int CHINA_ID_MAX_LENGTH = 18;


	public static final String ADDRESS = "住址";
	public static final String BIRTH = "出生";
	public static final String GENDER = "性别";
	public static final String NAME = "姓名";
	public static final String NATION = "民族";
	public static final String NUM = "公民身份号码";


	private String province;
	private String city;
	private String district;
	private IntInstant birthday;
	private Integer gender;
	private int age;
	/**
	 * 身份证号码
	 */
	private String number;

	/**
	 * 每位加权因子
	 */
	public static final int power[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

	/**
	 * 第18位校检码
	 */
	public static final String verifyCode[] = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
	/**
	 * 最低年限
	 */
	public static final int MIN = 1930;

	/**
	 * 出生证明格式校验正则
	 */
	private static Pattern birthPattern = Pattern.compile("[A-Z]{1}\\d{9}");

	/**
	 * 初始化身份证号码解析类
	 *
	 * @param number
	 * @return
	 */
	public static IdCard tryParse(String number) {
		// 校验长度
		if (Strings.isNullOrEmpty(number)) {
			return null;
		}
		int len = number.length();
		if (len == CHINA_ID_MIN_LENGTH) {
			if (!validateIdCard15(number)) {
				// 15位号码验证失败
				return null;
			}
		} else if (len == CHINA_ID_MAX_LENGTH) {
			if (!validateIdCard18(number)) {
				// 18为号码验证失败
				return null;
			}
		} else {
			// 长度不对
			return null;
		}

		// 如果是15位要转换位18位

		IdCard idCard = new IdCard();
		idCard.setNumber(number);

		// 解读身份证
		number = len == CHINA_ID_MIN_LENGTH ? toNumber18(number) : number;
		ByteOpr byteOpr = new ByteOpr(number.getBytes());
		idCard.setProvince(new String(byteOpr.nextBytes(2)));
		idCard.setCity(new String(byteOpr.nextBytes(2)));
		idCard.setDistrict(new String(byteOpr.nextBytes(2)));
		idCard.setBirthday(IntInstant.ofIntDate(new String(byteOpr.nextBytes(8))));

		byteOpr.nextBytes(2);// 移位

		Integer iGender = Ints.tryParse(new String(byteOpr.nextBytes(1)));
		if (iGender == null) {
			// Gender.UNKNOWN
			idCard.setGender(0);
		} else if (iGender % 2 != 0) {
			// Gender.MALE
			idCard.setGender(1);
		} else {
			// Gender.FEMALE
			idCard.setGender(2);
		}

		IntInstant birthday = idCard.getBirthday();
		IntInstant now = IntInstant.now();
		int iCurrYear = now.getYear();
		// 计算基本年龄(周岁)
		int baseAge = iCurrYear - birthday.getYear() - 1;
		// 计算今年是否足岁
		// 今年生日
		int birthdayThisYear = birthday.addYear(baseAge + 1).getDate();
		// 今天
		int today = now.getDate();
		// 生日当天为新的一岁
		int age = baseAge + (birthdayThisYear < today ? 0 : 1);
		idCard.setAge(age);

		return idCard;
	}

	/**
	 * 校验出生证明格式
	 *
	 * @param no
	 * @return
	 */
	public static boolean validBirthNo(String no) {
		if (Strings.isNullOrEmpty(no)) {
			return false;
		}
		no = no.toUpperCase();
		return birthPattern.matcher(no).matches();
	}

	/**
	 * 将15位身份证号码转换为18位
	 *
	 * @param number 15位身份编码
	 * @return 18位身份编码
	 */
	private static String toNumber18(String number) {
		String idCard18;
		if (number.length() == CHINA_ID_MAX_LENGTH) {
			return number;
		}
		// 获取出生年月日
		String birthday = number.substring(6, 12);
		IntInstant birthDate = IntInstant.ofIntDate(birthday);
		// 获取出生年(完全表现形式,如：2010)
		String sYear = String.valueOf(birthDate.getYear());
		idCard18 = number.substring(0, 6) + sYear + number.substring(8);
		// 转换字符数组
		char[] cArr = idCard18.toCharArray();
		int[] iCard = converCharToInt(cArr);
		int iSum17 = getPowerSum(iCard);
		// 获取校验位
		String sVal = getCheckCode18(iSum17);
		if (sVal.length() > 0) {
			idCard18 += sVal;
		} else {
			return null;
		}
		return idCard18;
	}

	/**
	 * 验证18位身份编码是否合法
	 *
	 * @param number 身份编码
	 * @return 是否合法
	 */
	private static boolean validateIdCard18(String number) {
		boolean bTrue = false;
		if (number.length() == CHINA_ID_MAX_LENGTH) {
			// 前17位
			String code17 = number.substring(0, 17);
			// 第18位
			String code18 = number.substring(17, CHINA_ID_MAX_LENGTH);
			if (isNum(code17)) {
				char[] cArr = code17.toCharArray();
				if (cArr != null) {
					int[] iCard = converCharToInt(cArr);
					int iSum17 = getPowerSum(iCard);
					// 获取校验位
					String val = getCheckCode18(iSum17);
					if (val.length() > 0) {
						if (val.equalsIgnoreCase(code18)) {
							bTrue = true;
						}
					}
				}
			}
		}
		return bTrue;
	}

	/**
	 * 验证15位身份编码是否合法
	 *
	 * @param number 身份编码
	 * @return 是否合法
	 */
	public static boolean validateIdCard15(String number) {
		if (number.length() != CHINA_ID_MIN_LENGTH) {
			return false;
		}
		if (isNum(number)) {
//			String proCode = number.substring(0, 2);
			String birthCode = number.substring(6, 12);
			Date birthDate = null;
			try {
				birthDate = new SimpleDateFormat("yy").parse(birthCode.substring(0, 2));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cal = Calendar.getInstance();
			if (birthDate != null)
				cal.setTime(birthDate);
			if (!valiDate(cal.get(Calendar.YEAR), Integer.valueOf(birthCode.substring(2, 4)),
				Integer.valueOf(birthCode.substring(4, 6)))) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 将字符数组转换成数字数组
	 *
	 * @param ca 字符数组
	 * @return 数字数组
	 */
	public static int[] converCharToInt(char[] ca) {
		int len = ca.length;
		int[] iArr = new int[len];
		try {
			for (int i = 0; i < len; i++) {
				iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return iArr;
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 *
	 * @param iArr
	 * @return 身份证编码。
	 */
	public static int getPowerSum(int[] iArr) {
		int iSum = 0;
		if (power.length == iArr.length) {
			for (int i = 0; i < iArr.length; i++) {
				for (int j = 0; j < power.length; j++) {
					if (i == j) {
						iSum = iSum + iArr[i] * power[j];
					}
				}
			}
		}
		return iSum;
	}

	/**
	 * 将power和值与11取模获得余数进行校验码判断
	 *
	 * @param iSum
	 * @return 校验位
	 */
	public static String getCheckCode18(int iSum) {
		String sCode = "";
		switch (iSum % 11) {
			case 10:
				sCode = "2";
				break;
			case 9:
				sCode = "3";
				break;
			case 8:
				sCode = "4";
				break;
			case 7:
				sCode = "5";
				break;
			case 6:
				sCode = "6";
				break;
			case 5:
				sCode = "7";
				break;
			case 4:
				sCode = "8";
				break;
			case 3:
				sCode = "9";
				break;
			case 2:
				sCode = "x";
				break;
			case 1:
				sCode = "0";
				break;
			case 0:
				sCode = "1";
				break;
		}
		return sCode;
	}

	/**
	 * 根据身份编号获取户籍省份
	 *
	 * @return 省级编码。
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 获取身份证户籍城市编码
	 *
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 获取身份证户籍区
	 *
	 * @return
	 */
	public String getDistinct() {
		return district;
	}

	/**
	 * 数字验证
	 *
	 * @param val
	 * @return 提取的数字。
	 */
	private static boolean isNum(String val) {
		return val == null || "".equals(val) ? false : val.matches("^[0-9]*$");
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * 验证小于当前日期 是否有效
	 *
	 * @param iYear  待验证日期(年)
	 * @param iMonth 待验证日期(月 1-12)
	 * @param iDate  待验证日期(日)
	 * @return 是否有效
	 */
	private static boolean valiDate(int iYear, int iMonth, int iDate) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int datePerMonth;
		if (iYear < MIN || iYear >= year) {
			return false;
		}
		if (iMonth < 1 || iMonth > 12) {
			return false;
		}
		switch (iMonth) {
			case 4:
			case 6:
			case 9:
			case 11:
				datePerMonth = 30;
				break;
			case 2:
				boolean dm = ((iYear % 4 == 0 && iYear % 100 != 0) || (iYear % 400 == 0)) && (iYear > MIN && iYear < year);
				datePerMonth = dm ? 29 : 28;
				break;
			default:
				datePerMonth = 31;
		}
		return (iDate >= 1) && (iDate <= datePerMonth);
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setBirthday(IntInstant birthday) {
		this.birthday = birthday;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public IntInstant getBirthday() {
		return birthday;
	}

	/**
	 * 身份证脱敏，保持前6位后3位，中间使用*替换
	 *
	 * @param idNo
	 * @return
	 */
	public static String encrypt(String idNo) {
		if (Strings.isNullOrEmpty(idNo) || idNo.length() <= 10) {
			return idNo;
		}
		return idNo.replaceAll("(?<=\\w{6})\\w(?=\\w{3})", "*");
	}

	@Override
	public String toString() {
		return "IdCard [province=" + province + ", city=" + city + ", district=" + district + ", birthday=" + birthday
				   + ", gender=" + gender + ", age=" + age + ", number=" + number + "]";
	}

}
