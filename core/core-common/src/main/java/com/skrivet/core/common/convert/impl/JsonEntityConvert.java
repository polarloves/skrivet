package com.skrivet.core.common.convert.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skrivet.core.common.convert.EntityConvert;
import com.skrivet.core.common.exception.ConvertExp;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonEntityConvert implements EntityConvert {
    public ObjectMapper commonMapper = new ObjectMapper();
    public ObjectMapper skipErrorMapper = new ObjectMapper();

    {
        commonMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        commonMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        commonMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        skipErrorMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        skipErrorMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        skipErrorMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        skipErrorMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public <T> T convert(Object source, Class<T> cls, boolean skip) {
        try {
            if (source == null) {
                return null;
            }
            ObjectMapper objectMapper = skip ? skipErrorMapper : commonMapper;
            return objectMapper.readValue(objectMapper.writeValueAsBytes(source), cls);
        } catch (Exception e) {
            throw new ConvertExp().variable(cls.toString()).copyStackTrace(e);
        }
    }

    @Override
    public <T> T convert(Object source, Class<T> cls) {
        return convert(source, cls, false);
    }

    @Override
    public <T> T convertIgnoreFiledError(Object source, Class<T> cls) {
        return convert(source, cls, true);
    }

    @Override
    public <T> List<T> convertList(List<?> sources, Class<T> cls) {
        return convertList(sources, cls, false);
    }

    @Override
    public <T> List<T> convertListIgnoreFiledError(List<?> sources, Class<T> cls) {
        return convertList(sources, cls, true);
    }


    public <T> List<T> convertList(List<?> sources, Class<T> cls, boolean skip) {
        try {
            if (CollectionUtils.isEmpty(sources)) {
                return null;
            }
            ObjectMapper objectMapper = skip ? skipErrorMapper : commonMapper;
            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, cls);
            return objectMapper.readValue(objectMapper.writeValueAsBytes(sources), javaType);
        } catch (Exception e) {
            throw new ConvertExp().variable(cls.toString()).copyStackTrace(e);
        }
    }
}
