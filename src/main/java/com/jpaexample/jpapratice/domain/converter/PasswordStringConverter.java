package com.jpaexample.jpapratice.domain.converter;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class PasswordStringConverter implements AttributeConverter<Set<String>,String> {

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        if(attribute==null || attribute.isEmpty()) return null;
        return String.join(",", attribute);
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        if(StringUtils.isBlank(dbData)) return new HashSet<>();
        return Arrays.stream(dbData.split(",")).collect(Collectors.toSet());
    }
}
