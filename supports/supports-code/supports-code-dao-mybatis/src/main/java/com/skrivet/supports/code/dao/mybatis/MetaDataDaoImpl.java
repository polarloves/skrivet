package com.skrivet.supports.code.dao.mybatis;

import com.skrivet.core.common.exception.UnknownExp;
import com.skrivet.supports.code.dao.core.form.MetaDataDao;
import com.skrivet.supports.code.dao.core.form.entity.select.TableColumnMetaDataDP;
import com.skrivet.supports.code.dao.core.form.entity.select.TableMetaDataDP;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MetaDataDaoImpl implements MetaDataDao {
    @Resource(name = "codeSqlSessionFactory")
    private SqlSessionFactory sqlSessionFactory;


    @Override
    public TableMetaDataDP findMetaData(String name) {
        Connection conn = null;
        TableMetaDataDP result = new TableMetaDataDP();
        SqlSession sqlSession = null;
        try {
            String primaryKeyName = null;
            sqlSession = sqlSessionFactory.openSession();
            conn = sqlSession.getConnection();
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet keys = databaseMetaData.getPrimaryKeys(null, null, name);
            if (keys.next()) {
                primaryKeyName = keys.getString(4);
            }
            keys.close();
            ResultSet resultSet = databaseMetaData.getTables(null, "%", name, new String[]{"TABLE"});
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.equalsIgnoreCase(name)) {
                    result.setName(tableName);
                    result.setComment(resultSet.getString("REMARKS"));
                    break;
                }
            }
            if (StringUtils.isEmpty(result.getComment())) {
                result.setComment("未知");
            }
            result.setModuleName(JdbcUtils.convertUnderscoreNameToPropertyName(result.getName()));
            resultSet.close();
            List<TableColumnMetaDataDP> columns = new ArrayList<>();
            resultSet = conn.getMetaData().getColumns(null, null, result.getName(), "%");
            while (resultSet.next()) {
                String comment = resultSet.getString("REMARKS");
                if (comment.contains(",")) {
                    comment = comment.substring(0, comment.indexOf(","));
                }
                if (comment.contains("，")) {
                    comment = comment.substring(0, comment.indexOf("，"));
                }
                if (comment.contains("(")) {
                    comment = comment.substring(0, comment.indexOf("("));
                }
                if (comment.contains("（")) {
                    comment = comment.substring(0, comment.indexOf("（"));
                }
                if(StringUtils.isEmpty(comment)){
                    comment="未知";
                }
                TableColumnMetaDataDP column = new TableColumnMetaDataDP();
                column.setName(resultSet.getString("COLUMN_NAME"));
                column.setRemark(comment);
                column.setPrimary(column.getName().equalsIgnoreCase(primaryKeyName));
                column.setNullAble(resultSet.getString("IS_NULLABLE").equalsIgnoreCase("YES"));
                column.setLength(resultSet.getInt("COLUMN_SIZE"));
                column.setJavaName(JdbcUtils.convertUnderscoreNameToPropertyName(column.getName()));
                column.setJavaType(obtainJavaType(resultSet.getString("TYPE_NAME")));
                columns.add(column);
            }
            resultSet.close();
            result.setColumns(columns);
            return result;
        } catch (Exception e) {
            throw new UnknownExp().copyStackTrace(e);
        } finally {
            sqlSession.close();
        }

    }

    private String obtainJavaType(String type) {
        return "String";
    }

    @Override
    public boolean addColumn(String tableName, String columnName, int type, String remark, int length) {
        SqlSession sqlSession = null;
        String sql = "alter table " + tableName + " add " + columnName;
        if (type == 1) {
            sql = sql + " varchar(" + length + ")";
        } else if (type == 2) {
            sql = sql + " datetime(" + length + ")";
        }
        try {
            sqlSession = sqlSessionFactory.openSession();
            Connection conn = sqlSession.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.execute();
        } catch (Exception e) {
            throw new UnknownExp().copyStackTrace(e);
        } finally {
            sqlSession.close();
        }
        return false;
    }
}
