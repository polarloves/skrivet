package com.skrivet.components.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedJdbcTypes(value = {JdbcType.DATE, JdbcType.TIME})
public class DateStringTypeHandler extends BaseTypeHandler<String> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String date, JdbcType jdbcType) throws SQLException {
        try {
            preparedStatement.setDate(i, date == null ? null : new java.sql.Date(sdf.parse(date).getTime()));
        } catch (Exception e) {
            throw new SQLException();
        }
    }

    private String formatValue(java.sql.Date sqlDate) {
        if (sqlDate != null) {
            return sdf.format(new Date(sqlDate.getTime()));
        }
        return null;
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return formatValue(resultSet.getDate(s));
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return formatValue(resultSet.getDate(i));
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return formatValue(callableStatement.getDate(i));
    }
}
