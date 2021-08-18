package com.skrivet.components.mybatis.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VarcharJsonTypeHandler<T> extends BaseTypeHandler<T> {
    private static final String DEFAULT_CHARSET = "utf-8";
    private ObjectMapper objectMapper;

    {
        this.objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T dJson, JdbcType jdbcType) throws SQLException {
        try {
            byte[] bs = objectMapper.writeValueAsBytes(dJson);
            preparedStatement.setString(i, new String(bs, DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new SQLException();
        }
    }

    private T formatValue(String value) throws SQLException {
        if (value != null) {
            try {
                return (T) objectMapper.readValue(value, Object.class);
            } catch (JsonProcessingException e) {
                throw new SQLException();
            }
        }
        return null;
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return formatValue(resultSet.getString(s));
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return formatValue(resultSet.getString(i));
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return formatValue(callableStatement.getString(i));
    }
}
