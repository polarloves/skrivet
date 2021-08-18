package com.skrivet.plugins.database.mongodb.dao;

import com.skrivet.core.common.convert.EntityConvert;
import com.skrivet.core.common.exception.UnknownExp;
import com.skrivet.plugins.database.mongodb.annotations.Query;
import com.skrivet.plugins.database.mongodb.annotations.Sortable;
import com.skrivet.plugins.database.mongodb.enums.MatchStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

public class MongoDao {
    @Autowired
    protected EntityConvert entityConvert;
    protected MongoTemplate mongoTemplate;
    protected GridFsTemplate gridFsTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public GridFsTemplate getGridFsTemplate() {
        return gridFsTemplate;
    }

    public void setGridFsTemplate(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public Object getValue(Object object, String name) {
        try {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(object.getClass(), name);
            Method method = propertyDescriptor.getReadMethod();
            return method.invoke(object);
        } catch (Exception e) {
            throw new UnknownExp().copyStackTrace(e);
        }
    }

    public <T> Update buildUpdate(Object object, Class<T> clz, String... ignoreFieldNames) {
        Update update = new Update();
        ReflectionUtils.doWithFields(clz, field -> {
            String name = field.getName();
            String fieldName = name;
            if (field.getAnnotation(Field.class) != null) {
                fieldName = field.getAnnotation(Field.class).value();
            }
            boolean ignore = isInCollection(name, ignoreFieldNames);
            if (!ignore) {
                update.set(fieldName, getValue(object, name));
            }
        });
        return update;
    }

    private boolean isInCollection(String name, String... names) {
        boolean ignore = false;
        if (names != null && names.length > 0) {
            for (String ignoreFieldName : names) {
                if (ignoreFieldName.equals(name)) {
                    ignore = true;
                    break;
                }
            }
        }
        return ignore;
    }

    public <T> org.springframework.data.mongodb.core.query.Query buildQuery(Object object, Class<T> clz) {
        return buildQuery(object, clz,null,null);
    }

    public <T> org.springframework.data.mongodb.core.query.Query buildQuery(Object object, Class<T> clz, String sortName, String order) {
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        ReflectionUtils.doWithFields(clz, field -> {
            String name = field.getName();
            String fieldName = name;
            if (field.getAnnotation(Field.class) != null) {
                fieldName = field.getAnnotation(Field.class).value();
            }
            if (field.getAnnotation(Query.class) != null) {
                Object value = getValue(object, name);
                if (value != null) {
                    if (value instanceof String && StringUtils.isEmpty(value)) {
                        return;
                    }
                    MatchStyle matchStyle = field.getAnnotation(Query.class).value();
                    switch (matchStyle) {
                        case GT:
                            query.addCriteria(Criteria.where(fieldName).gt(value));
                            break;
                        case LT:
                            query.addCriteria(Criteria.where(fieldName).lt(value));
                            break;
                        case GTE:
                            query.addCriteria(Criteria.where(fieldName).gte(value));
                            break;
                        case LTE:
                            query.addCriteria(Criteria.where(fieldName).lte(value));
                            break;
                        case LIKE:
                            query.addCriteria(Criteria.where(fieldName).regex("^.*" + value.toString() + ".*$"));
                            break;
                        case EQUAL:
                            query.addCriteria(Criteria.where(fieldName).is(value));
                            break;
                        case LEFT_LIKE:
                            query.addCriteria(Criteria.where(fieldName).regex("^" + value.toString() + ".*$"));
                            break;
                        case RIGHT_LIKE:
                            query.addCriteria(Criteria.where(fieldName).regex("^.*" + value.toString() + "$"));
                            break;
                    }

                }
            }
            if (field.getAnnotation(Sortable.class) != null) {
                if (name.equals(sortName)) {
                    if (order.equalsIgnoreCase("ASC")) {
                        query.with(org.springframework.data.domain.Sort.by(
                                org.springframework.data.domain.Sort.Order.asc(fieldName)
                        ));
                    } else {
                        query.with(org.springframework.data.domain.Sort.by(
                                org.springframework.data.domain.Sort.Order.desc(fieldName)
                        ));
                    }

                }
            }
        });
        return query;
    }

    public org.springframework.data.mongodb.core.query.Query buildEqualQuery(org.springframework.data.mongodb.core.query.Query query, String name, Object value) {
        if (value != null) {
            if (value instanceof String && StringUtils.isEmpty(value)) {
                return query;
            }
            query.addCriteria(Criteria.where(name).is(value));
        }
        return query;
    }

    public org.springframework.data.mongodb.core.query.Query buildLikeQuery(org.springframework.data.mongodb.core.query.Query query, String name, Object value) {
        if (value != null) {
            if (value instanceof String && StringUtils.isEmpty(value)) {
                return query;
            }
            query.addCriteria(Criteria.where(name).regex(".*?\\" + value.toString() + ".*"));
        }
        return query;
    }
}
