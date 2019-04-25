package com.hitales.national.migrate.converter;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListLongConverter implements AttributeConverter<List<Long>, String> {

	/**
	 * 分隔符
	 */
	private static final String SEPARATOR = ",";

	@Override
	public String convertToDatabaseColumn(List<Long> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return "";
		} else {
			return attribute.stream()
					   .map(Objects::toString)
					   .collect(Collectors.joining(SEPARATOR));
		}
	}

	@Override
	public List<Long> convertToEntityAttribute(String dbData) {
		if (Strings.isNullOrEmpty(dbData)) {
			return Lists.newArrayList();
		} else {
			return Splitter.on(SEPARATOR)
					   .omitEmptyStrings().trimResults()
					   .splitToList(dbData)
					   .stream()
					   .map(Longs::tryParse)
					   .filter(Objects::nonNull)
					   .collect(Collectors.toList());
		}
	}

}
