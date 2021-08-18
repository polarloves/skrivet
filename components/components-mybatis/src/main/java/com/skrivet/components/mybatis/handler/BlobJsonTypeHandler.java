package com.skrivet.components.mybatis.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class BlobJsonTypeHandler<T> extends BaseTypeHandler<T> {
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
            ByteArrayInputStream bis = new ByteArrayInputStream(bs);
            preparedStatement.setBinaryStream(i, bis, bs.length);
        } catch (JsonProcessingException e) {
            throw new SQLException();
        }
    }

    private T formatValue(Blob blob) throws SQLException {
        byte[] returnValue = null;
        if (null != blob) {
            returnValue = blob.getBytes(1, (int) blob.length());
        } else {
            return null;
        }
        try {
            return (T) objectMapper.readValue(returnValue, Object.class);
        } catch (Exception e) {
            throw new SQLException();
        }
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return formatValue(resultSet.getBlob(s));
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return formatValue(resultSet.getBlob(i));
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return formatValue(callableStatement.getBlob(i));
    }
}
