package com.skrivet.supports.code.service.impl.form.handler;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonFormParameterHandler implements FormParameterHandler {
    @Override
    public void process(Map<String, Object> value) {
        replace(value, "dbTableName", "table");
        replace(value, "moduleName", "name");
        replace(value, "formName", "nameCn");
        value.remove("id");
        value.remove("parentCode");
        value.put("javaName",value.get("name").toString().substring(0,1).toUpperCase()+value.get("name").toString().substring(1));
      //  value.remove("template");
    }

    private void replace(Map<String, Object> value, String org, String target) {
        value.put(target, value.get(org));
        value.remove(org);
    }

    @Override
    public int order() {
        return 0;
    }
}
