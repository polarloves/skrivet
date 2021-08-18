package com.skrivet.supports.data.dao.mongodb.tree;


import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.data.dao.core.tree.entity.add.TreeAddDQ;
import com.skrivet.supports.data.dao.core.tree.entity.select.TreeDetailDP;
import com.skrivet.supports.data.dao.core.tree.entity.select.TreeGroupDP;
import com.skrivet.supports.data.dao.core.tree.entity.select.TreeListDP;
import com.skrivet.supports.data.dao.core.tree.entity.select.TreeSelectPageDQ;
import com.skrivet.supports.data.dao.core.tree.entity.update.TreeUpdateDQ;
import com.skrivet.supports.data.dao.mongodb.tree.template.TreeTemplate;
import com.skrivet.supports.data.dao.mongodb.tree.template.select.TreeSelectPageTemplate;
import com.skrivet.supports.data.dao.mongodb.tree.template.update.TreeUpdateTemplate;
import org.bson.Document;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public class TreeDao extends MongoDao implements com.skrivet.supports.data.dao.core.tree.TreeDao {
    public static final String COLLECTION_NAME = "skrivet_tree";

    @Override
    public void insert(TreeAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, TreeTemplate.class));

    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)), COLLECTION_NAME).getDeletedCount();

    }

    @Override
    public Long update(TreeUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("id").is(entity.getId())),
                buildUpdate(entity, TreeUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    @Override
    public TreeDetailDP selectOneById(String id) {
        return entityConvert.convertIgnoreFiledError(mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), TreeTemplate.class), TreeDetailDP.class);
    }

    @Override
    public TreeDetailDP selectOneByValue(String groupId, String value) {
        return entityConvert.convertIgnoreFiledError(mongoTemplate.findOne(new Query(Criteria.where("groupId").is(groupId)).addCriteria(Criteria.where("value").is(value)), TreeTemplate.class), TreeDetailDP.class);
    }

    @Override
    public List<TreeListDP> selectPageList(TreeSelectPageDQ condition) {
        return entityConvert.convertListIgnoreFiledError(
                mongoTemplate.find(
                        buildQuery(condition, TreeSelectPageTemplate.class, condition.getSort(), condition.getOrder())
                                .skip(condition.getPageStartNumber())
                                .limit(condition.getPageOffsetNumber()),
                        TreeTemplate.class, COLLECTION_NAME),
                TreeListDP.class);
    }

    @Override
    public List<TreeListDP> selectListByGroupId(String groupId) {
        return entityConvert.convertListIgnoreFiledError(
                mongoTemplate.find(
                        new Query(Criteria.where("groupId").is(groupId)).with(Sort.by(Sort.Order.asc("orderNum"))),
                        TreeTemplate.class, COLLECTION_NAME),
                TreeListDP.class);
    }

    @Override
    public Long selectPageCount(TreeSelectPageDQ condition) {
        return mongoTemplate.count(buildQuery(condition, TreeSelectPageTemplate.class), COLLECTION_NAME);
    }

    @Override
    public List<TreeGroupDP> groups() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project("groupName", "groupId"),
                Aggregation.group("groupName", "groupId"),
                Aggregation.sort(Sort.by(Sort.Direction.ASC, "groupName"))
        );
        List<TreeGroupDP> details = new ArrayList<TreeGroupDP>();
        AggregationResults<Document> outputType = mongoTemplate.aggregate(agg, COLLECTION_NAME, Document.class);
        List<Document> resultList = outputType.getMappedResults();
        for (Document obj : resultList) {
            Document doc = obj.get("_id", Document.class);
            TreeGroupDP entity = new TreeGroupDP();
            entity.setText(doc.getString("groupName"));
            entity.setValue(doc.getString("groupId"));
            details.add(entity);
        }
        return details;
    }
}
