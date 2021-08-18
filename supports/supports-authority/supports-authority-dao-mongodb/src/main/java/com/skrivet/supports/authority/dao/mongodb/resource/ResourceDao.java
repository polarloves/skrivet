package com.skrivet.supports.authority.dao.mongodb.resource;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.authority.dao.core.resource.entity.add.ResourceAddDQ;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceDetailDP;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceListDP;
import com.skrivet.supports.authority.dao.core.resource.entity.select.ResourceSelectPageDQ;
import com.skrivet.supports.authority.dao.core.resource.entity.update.ResourceUpdateDQ;
import com.skrivet.supports.authority.dao.mongodb.resource.template.ResourceTemplate;
import com.skrivet.supports.authority.dao.mongodb.resource.template.select.ResourceSelectPageTemplate;
import com.skrivet.supports.authority.dao.mongodb.resource.template.update.ResourceUpdateTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
@Dao
public class ResourceDao  extends MongoDao implements com.skrivet.supports.authority.dao.core.resource.ResourceDao {
    public static final String COLLECTION_NAME = "skrivet_resource";
    @Override
    public void insert(ResourceAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, ResourceTemplate.class));
    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("id").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    @Override
    public Long update(ResourceUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, ResourceUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    @Override
    public ResourceDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), ResourceTemplate.class),
                ResourceDetailDP.class);
    }

    @Override
    public ResourceDetailDP selectOneByValue(String value) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("value").is(value)), ResourceTemplate.class),
                ResourceDetailDP.class);
    }

    @Override
    public List<ResourceListDP> selectList() {
        return entityConvert.convertList(mongoTemplate.find(new Query(), ResourceTemplate.class), ResourceListDP.class);

    }

    @Override
    public List<ResourceListDP> selectPageList(ResourceSelectPageDQ condition) {
        return entityConvert.convertListIgnoreFiledError(mongoTemplate.find(
                buildQuery(condition, ResourceSelectPageTemplate.class, condition.getSort(), condition.getOrder())
                        .skip(condition.getPageStartNumber())
                        .limit(condition.getPageOffsetNumber()),
                ResourceTemplate.class, COLLECTION_NAME), ResourceListDP.class);
    }

    @Override
    public Long selectPageCount(ResourceSelectPageDQ condition) {
        return mongoTemplate.count(buildQuery(condition, ResourceSelectPageTemplate.class), ResourceTemplate.class, COLLECTION_NAME);
    }
}
