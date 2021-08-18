package com.skrivet.supports.data.dao.mongodb.dict;

import com.skrivet.supports.data.dao.mongodb.dict.template.DictTemplate;
import org.bson.Document;
import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.data.dao.core.dict.entity.add.DictAddDQ;
import com.skrivet.supports.data.dao.core.dict.entity.select.DictDetailDP;
import com.skrivet.supports.data.dao.core.dict.entity.select.DictGroupDP;
import com.skrivet.supports.data.dao.core.dict.entity.select.DictListDP;
import com.skrivet.supports.data.dao.core.dict.entity.select.DictSelectPageDQ;
import com.skrivet.supports.data.dao.core.dict.entity.update.DictUpdateDQ;
import com.skrivet.supports.data.dao.mongodb.dict.template.select.DictSelectPageTemplate;
import com.skrivet.supports.data.dao.mongodb.dict.template.update.DictUpdateTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public class DictDao extends MongoDao implements com.skrivet.supports.data.dao.core.dict.DictDao {
    public static final String COLLECTION_NAME = "skrivet_dict";

    @Override
    public void insert(DictAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, DictTemplate.class));
    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("id").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    @Override
    public Long update(DictUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, DictUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    @Override
    public DictDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), DictTemplate.class),
                DictDetailDP.class);
    }

    @Override
    public List<DictListDP> selectListByGroupId(String groupId) {
        return entityConvert.convertList(
                mongoTemplate.find(
                        new Query(Criteria.where("groupId").is(groupId)).with(Sort.by(Sort.Order.asc("orderNum"))),
                        DictTemplate.class,
                        COLLECTION_NAME),
                DictListDP.class);
    }

    @Override
    public List<DictListDP> selectPageList(DictSelectPageDQ condition) {
        return entityConvert.convertList(
                mongoTemplate.find(
                        buildQuery(condition, DictSelectPageTemplate.class, condition.getSort(), condition.getOrder()).skip(condition.getPageStartNumber()).limit(condition.getPageOffsetNumber()),
                        DictTemplate.class,
                        COLLECTION_NAME),
                DictListDP.class);
    }

    @Override
    public Long selectPageCount(DictSelectPageDQ condition) {
        return mongoTemplate.count(buildQuery(condition, DictSelectPageTemplate.class), COLLECTION_NAME);
    }


    @Override
    public List<DictGroupDP> groups() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("groupName", "groupId"),
                Aggregation.group("groupName", "groupId"),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "groupName"))
        );
        List<DictGroupDP> details = new ArrayList<DictGroupDP>();
        AggregationResults<Document> outputType = mongoTemplate.aggregate(agg, COLLECTION_NAME, Document.class);
        List<Document> resultList = outputType.getMappedResults();
        for (Document obj : resultList) {
            Document doc = obj.get("_id", Document.class);
            DictGroupDP entity = new DictGroupDP();
            entity.setText(doc.getString("groupName"));
            entity.setValue(doc.getString("groupId"));
            details.add(entity);
        }
        return details;
    }
}
