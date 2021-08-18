package com.skrivet.supports.menu.dao.mongodb.item;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.menu.dao.core.item.entity.add.*;
import com.skrivet.supports.menu.dao.core.item.entity.select.*;
import com.skrivet.supports.menu.dao.core.item.entity.update.*;
import com.skrivet.supports.menu.dao.mongodb.item.template.*;
import com.skrivet.supports.menu.dao.mongodb.item.template.update.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Dao
public class MenuItemDao extends MongoDao implements com.skrivet.supports.menu.dao.core.item.MenuItemDao {
    public static final String COLLECTION_NAME = "skrivet_menu";

    public void insert(MenuItemAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, MenuItemTemplate.class));
    }

    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("ID").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    public Long update(MenuItemUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("ID").is(entity.getId())), buildUpdate(entity, MenuItemUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    public MenuItemDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("ID").is(id)), MenuItemTemplate.class),
                MenuItemDetailDP.class);
    }

    public List<MenuItemListDP> selectList() {
        return entityConvert.convertList(
                mongoTemplate.find(
                        new Query(),
                        MenuItemTemplate.class,
                        COLLECTION_NAME),
                MenuItemListDP.class);
    }

    public Long selectCountByParentKey(String parentKey) {
        return mongoTemplate.count(new Query(Criteria.where("PARENT_ID").is(parentKey)), COLLECTION_NAME);
    }

    @Override
    public List<String> templateItemIds(String templateId) {
        List<String> result = new ArrayList<>();
        List<TemplateItemTemplate> entities = mongoTemplate.find(new Query(Criteria.where("templateId").is(templateId)), TemplateItemTemplate.class);
        if (!CollectionUtils.isEmpty(entities)) {
            entities.forEach(entity -> result.add(entity.getItemId()));
        }
        return result;
    }

    @Override
    public Long deleteTemplateItemByTemplateId(String templateId) {
        return mongoTemplate.remove(
                new Query(Criteria.where("templateId").is(templateId)),
                "skrivet_menu_template_item")
                .getDeletedCount();
    }

    @Override
    public void insertTemplateItems(TemplateItemAddDQ condition) {
        if (!CollectionUtils.isEmpty(condition.getItems())) {
            condition.getItems().forEach(c -> {
                TemplateItemTemplate tmp = new TemplateItemTemplate();
                tmp.setItemId(c.getItemId());
                tmp.setTemplateId(condition.getTemplateId());
                mongoTemplate.insert(tmp);
            });
        }
    }
}