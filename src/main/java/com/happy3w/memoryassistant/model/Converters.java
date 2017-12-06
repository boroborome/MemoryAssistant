package com.happy3w.memoryassistant.model;

import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;

public class Converters {
    public static class StringArrayConverter implements AttributeConverter<String[], String> {

        public static final String SEPARATOR = ",";

        @Override
        public String convertToDatabaseColumn(String[] attribute) {
            if (attribute == null)
                return null;
            return String.join(SEPARATOR, attribute);
        }

        @Override
        public String[] convertToEntityAttribute(String dbData) {
            if (StringUtils.isEmpty(dbData))
                return new String[0];

            return dbData.split(SEPARATOR);
        }
    }

}
