package com.hitales.national.migrate.converter;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.hitales.commons.enums.EnumCollector;
import com.hitales.national.migrate.enums.PoorCause;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.stream.Collectors;

public class ListPoorCauseConverter implements AttributeConverter<List<PoorCause>, String> {

	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = ",";

	@Override
	public String convertToDatabaseColumn(List<PoorCause> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return "";
		} else {
			return attribute.stream()
					   .filter(cause -> cause != null)
					   .map(cause -> cause.getKey().toString())
					   .collect(Collectors.joining(SEPARATOR));
		}
	}

	@Override
	public List<PoorCause> convertToEntityAttribute(String dbData) {
		if (Strings.isNullOrEmpty(dbData)) {
			return Lists.newArrayList();
		} else {
			return Splitter.on(SEPARATOR).omitEmptyStrings().trimResults()
					   .splitToList(dbData)
					   .stream().map(str -> Ints.tryParse(str))
					   .filter(i -> i != null)
					   .map(i -> (PoorCause) EnumCollector.forClass(PoorCause.class).keyOf(i))
					   .filter(e -> e != null)
					   .collect(Collectors.toList());
		}
	}

}
