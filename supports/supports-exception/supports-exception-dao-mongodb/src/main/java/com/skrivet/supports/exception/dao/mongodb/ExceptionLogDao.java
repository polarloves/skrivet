package com.skrivet.supports.exception.dao.mongodb;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.exception.dao.core.entity.add.*;
import com.skrivet.supports.exception.dao.core.entity.select.*;
import com.skrivet.supports.exception.dao.mongodb.template.*;
import com.skrivet.supports.exception.dao.mongodb.template.select.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;


import java.util.List;

@Dao
public class ExceptionLogDao extends MongoDao implements com.skrivet.supports.exception.dao.core.ExceptionLogDao {
    public static final String COLLECTION_NAME = "SKRIVET_EXCEPTION";

    public void insert(ExceptionLogAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, ExceptionLogTemplate.class));
    }

    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("id").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    public ExceptionLogDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), ExceptionLogTemplate.class),
                ExceptionLogDetailDP.class);
    }

    public List<ExceptionLogListDP> selectPageList(ExceptionLogSelectPageDQ condition) {
        Query query = buildQuery(condition, ExceptionLogSelectPageTemplate.class, condition.getSort(), condition.getOrder()).skip(condition.getPageStartNumber()).limit(condition.getPageOffsetNumber());
        if (!StringUtils.isEmpty(condition.getStartTime())) {
            query.addCriteria(Criteria.where("happenTime").gte(condition.getStartTime()));
        }
        if (!StringUtils.isEmpty(condition.getEndTime())) {
            query.addCriteria(Criteria.where("happenTime").lte(condition.getEndTime()));
        }
        return entityConvert.convertList(mongoTemplate.find(query, ExceptionLogTemplate.class, COLLECTION_NAME), ExceptionLogListDP.class);
    }

    public Long selectPageCount(ExceptionLogSelectPageDQ condition) {
        return mongoTemplate.count(buildQuery(condition, ExceptionLogSelectPageTemplate.class), COLLECTION_NAME);
    }
}