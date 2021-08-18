package com.skrivet.supports.menu.dao.mongodb.template;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.menu.dao.core.template.entity.add.*;
import com.skrivet.supports.menu.dao.core.template.entity.select.*;
import com.skrivet.supports.menu.dao.core.template.entity.update.*;
import com.skrivet.supports.menu.dao.mongodb.item.template.TemplateItemTemplate;
import com.skrivet.supports.menu.dao.mongodb.template.template.*;
import com.skrivet.supports.menu.dao.mongodb.template.template.update.*;
import com.skrivet.supports.menu.dao.mongodb.template.template.select.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;

@Dao
public class MenuTemplateDao extends MongoDao implements com.skrivet.supports.menu.dao.core.template.MenuTemplateDao {
    public static final String COLLECTION_NAME = "skrivet_menu_template";

    public void insert(MenuTemplateAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, MenuTemplateTemplate.class));
    }

    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("ID").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    public Long update(MenuTemplateUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("ID").is(entity.getId())), buildUpdate(entity, MenuTemplateUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    public MenuTemplateDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("ID").is(id)), MenuTemplateTemplate.class),
                MenuTemplateDetailDP.class);
    }

    public List<MenuTemplateListDP> selectPageList(MenuTemplateSelectPageDQ condition) {
        return entityConvert.convertList(
                mongoTemplate.find(
                        buildQuery(condition, MenuTemplateSelectPageTemplate.class, condition.getSort(), condition.getOrder()).skip(condition.getPageStartNumber()).limit(condition.getPageOffsetNumber()),
                        MenuTemplateTemplate.class,
                        COLLECTION_NAME),
                MenuTemplateListDP.class);
    }

    public Long selectPageCount(MenuTemplateSelectPageDQ condition) {
        return mongoTemplate.count(buildQuery(condition, MenuTemplateSelectPageTemplate.class), COLLECTION_NAME);
    }

    @Override
    public Long clearDefault() {
        Update update = new Update();
        update.set("defaultTemplate", "0");
        return mongoTemplate.updateMulti(
                new Query(),
                update, COLLECTION_NAME).getMatchedCount();
    }

    @Override
    public Long setDefault(String id) {
        Update update = new Update();
        update.set("defaultTemplate", "1");
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("ID").is(id)),
                update, COLLECTION_NAME).getMatchedCount();
    }

    @Override
    public String defaultMenuTemplateId() {
        MenuTemplateDetailDP menuTemplateDetailDP = entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("DEFAULT_TEMPLATE").is("1")), MenuTemplateTemplate.class),
                MenuTemplateDetailDP.class);
        return menuTemplateDetailDP == null ? null : menuTemplateDetailDP.getId();
    }

    @Override
    public String userTemplate(String userId) {
        UserTemplateTemplate entity = mongoTemplate.findOne(new Query(Criteria.where("userId").is(userId)), UserTemplateTemplate.class);
        return entity == null ? null : entity.getTemplateId();
    }

    @Override
    public Long deleteUserTemplateByUserId(String userId) {
        return mongoTemplate.remove(
                new Query(Criteria.where("userId").is(userId)),
                "skrivet_user_menu_template")
                .getDeletedCount();
    }

    @Override
    public void insertUserTemplate(String id, String userId, String templateId) {
        UserTemplateTemplate tmp = new UserTemplateTemplate();
        tmp.setTemplateId(templateId);
        tmp.setUserId(userId);
        mongoTemplate.insert(tmp);
    }

    @Override
    public List<MenuTemplateListDP> selectList() {
        return entityConvert.convertList(
                mongoTemplate.find(
                        new Query(),
                        MenuTemplateTemplate.class,
                        COLLECTION_NAME),
                MenuTemplateListDP.class);
    }
}