package com.skrivet.components.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.NVARCHAR})
public class VarcharDateTypeHandler extends BaseTypeHandler<Date> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Date date, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, date == null ? null : sdf.format(date));
    }

    private Date formatValue(String value) throws SQLException {
        if (value != null) {
            try {
                return sdf.parse(value);
            } catch (ParseException e) {
                throw  new SQLException();
            }
        }
        return null;
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return formatValue(resultSet.getString(s));
    }

    @Override
    public Date getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return formatValue(resultSet.getString(i));
    }

    @Override
    public Date getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return formatValue(callableStatement.getString(i));
    }
}
