package com.skrivet.supports.code.service.impl.form.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ColumnParameterHandler implements FormParameterHandler {
    @Override
    public void process(Map<String, Object> value) {
        List<Map<String, Object>> columns = (List<Map<String, Object>>) value.get("columns");
        Map<String, Object> contains = new HashMap<>();
        for (Map<String, Object> column : columns) {
            String javaName = column.get("javaName").toString();
            column.put("javaCastName", javaName.substring(0, 1).toUpperCase() + javaName.substring(1));
            column.remove("formId");
            contains.put("viewType" + column.get("viewType"), true);
            contains.put("javaType" + column.get("javaType"), true);
        }
        value.putAll(contains);
        Map<String, Object> primaryKey = new HashMap<>();
        Map<String, Object> parentKey = new HashMap<>();
        for (Map<String, Object> obj : columns) {
            if ((Boolean) obj.get("primaryKey")) {
                primaryKey = obj;
            }
            if ((Boolean) obj.get("parentKey")) {
                parentKey = obj;
            }
        }
        columns.remove(primaryKey);
        if(parentKey!=null){
            columns.remove(parentKey);
        }
        value.put("primaryKey", primaryKey);
        value.put("parentKey", parentKey);
    }

    @Override
    public int order() {
        return 2;
    }
}
