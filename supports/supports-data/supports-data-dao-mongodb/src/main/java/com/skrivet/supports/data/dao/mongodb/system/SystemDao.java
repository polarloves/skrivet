package com.skrivet.supports.data.dao.mongodb.system;

import com.mongodb.client.result.UpdateResult;
import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.data.dao.core.system.entity.add.SystemAddDQ;
import com.skrivet.supports.data.dao.core.system.entity.select.SystemDetailDP;
import com.skrivet.supports.data.dao.core.system.entity.select.SystemGroupDP;
import com.skrivet.supports.data.dao.core.system.entity.select.SystemListDP;
import com.skrivet.supports.data.dao.core.system.entity.select.SystemSelectPageDQ;
import com.skrivet.supports.data.dao.core.system.entity.update.SystemUpdateDQ;
import com.skrivet.supports.data.dao.mongodb.system.template.SystemTemplate;
import com.skrivet.supports.data.dao.mongodb.system.template.select.SystemSelectPageTemplate;
import com.skrivet.supports.data.dao.mongodb.system.template.update.SystemUpdateTemplate;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public class SystemDao extends MongoDao implements com.skrivet.supports.data.dao.core.system.SystemDao {
    public static final String COLLECTION_NAME = "skrivet_system";

    @Override
    public void insert(SystemAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, SystemTemplate.class));
    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)), COLLECTION_NAME).getDeletedCount();

    }

    @Override
    public Long update(SystemUpdateDQ entity) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, SystemUpdateTemplate.class), COLLECTION_NAME).getMatchedCount();

    }

    @Override
    public SystemDetailDP selectOneById(String id) {
        return entityConvert.convertIgnoreFiledError(
                mongoTemplate.findOne(
                        new Query(Criteria.where("id").is(id)),
                        SystemTemplate.class),
                SystemDetailDP.class);

    }

    @Override
    public SystemDetailDP selectByGroupKey(String groupId, String key) {
        return entityConvert.convertIgnoreFiledError(
                mongoTemplate.find(
                        new Query(Criteria.where("groupId").is(groupId)).addCriteria(Criteria.where("sysKey").is(key)),
                        SystemTemplate.class, COLLECTION_NAME),
                SystemDetailDP.class);
    }

    @Override
    public List<SystemListDP> selectListByGroupId(String groupId) {
        return entityConvert.convertListIgnoreFiledError(
                mongoTemplate.find(
                        new Query(Criteria.where("groupId").is(groupId)).with(Sort.by(Sort.Order.asc("orderNum"))),
                        SystemTemplate.class, COLLECTION_NAME),
                SystemListDP.class);

    }

    @Override
    public List<SystemListDP> selectPageList(SystemSelectPageDQ condition) {
        return entityConvert.convertListIgnoreFiledError(mongoTemplate.find(
                buildQuery(condition, SystemSelectPageTemplate.class, condition.getSort(), condition.getOrder())
                        .skip(condition.getPageStartNumber())
                        .limit(condition.getPageOffsetNumber())
                , SystemTemplate.class, COLLECTION_NAME), SystemListDP.class);
    }

    @Override
    public Long selectPageCount(SystemSelectPageDQ condition) {
        return mongoTemplate.count(buildQuery(condition, SystemSelectPageTemplate.class), COLLECTION_NAME);
    }

    @Override
    public List<SystemGroupDP> groups() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("groupName", "groupId"),
                Aggregation.group("groupName", "groupId"),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "groupName"))
        );
        List<SystemGroupDP> details = new ArrayList<SystemGroupDP>();
        AggregationResults<Document> outputType = mongoTemplate.aggregate(agg, COLLECTION_NAME, Document.class);
        List<Document> resultList = outputType.getMappedResults();
        for (Document obj : resultList) {
            Document doc = obj.get("_id", Document.class);
            SystemGroupDP entity = new SystemGroupDP();
            entity.setText(doc.getString("groupName"));
            entity.setValue(doc.getString("groupId"));
            details.add(entity);
        }
        return details;
    }
}
