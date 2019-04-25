package com.hitales.national.migrate.entity;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.Arrays;

public interface CopyPartially {

	/**
	 * 从源对象中拷贝非空字段到本对象中
	 * <p>
	 * 该方法会拷贝所有的非空属性。使用时请注意是否有意图范围外的属性被意外拷贝。
	 *
	 * @param sourceObject 原对象
	 */
	default void copyNonNull(Object sourceObject) {
		BeanWrapper srcBean = new BeanWrapperImpl(sourceObject);

		// 过滤出值为空的属性
		String[] emptyProperties = Arrays.stream(srcBean.getPropertyDescriptors())
									   .filter(p -> srcBean.getPropertyValue(p.getName()) == null)
									   .map(PropertyDescriptor::getName).distinct().toArray(String[]::new);

		// 拷贝非空值
		BeanUtils.copyProperties(sourceObject, this, emptyProperties);
	}
}
