package com.skrivet.components.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(value = {JdbcType.VARCHAR, JdbcType.NVARCHAR, JdbcType.CHAR, JdbcType.INTEGER, JdbcType.SMALLINT})
public class IntVarcharBooleanTypeHandler extends BaseTypeHandler<Boolean> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Boolean aBoolean, JdbcType jdbcType) throws SQLException {
        switch (jdbcType) {
            case CHAR:
            case VARCHAR:
            case NVARCHAR:
                if (aBoolean == null) {
                    preparedStatement.setString(i, null);
                } else {
                    preparedStatement.setString(i, aBoolean ? "1" : "0");
                }
                break;
            case INTEGER:
            case SMALLINT:
                if (aBoolean == null) {
                    preparedStatement.setInt(i, -1);
                } else {
                    preparedStatement.setInt(i, aBoolean ? 1 : 0);
                }
                break;
        }

    }

    @Override
    public Boolean getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return formatValue(rs.getString(columnName));
    }

    private Boolean formatValue(String value) {
        Boolean result = null;
        if (value != null) {
            switch (value) {
                case "0":
                    result = false;
                    break;
                case "1":
                    result = true;
                    break;
            }
        }
        return result;
    }

    @Override
    public Boolean getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return formatValue(resultSet.getString(i));
    }

    @Override
    public Boolean getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return formatValue(callableStatement.getString(i));
    }
}
