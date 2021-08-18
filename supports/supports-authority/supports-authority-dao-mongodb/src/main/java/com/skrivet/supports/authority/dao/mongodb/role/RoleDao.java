package com.skrivet.supports.authority.dao.mongodb.role;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.authority.dao.core.role.entity.add.RoleAddDQ;
import com.skrivet.supports.authority.dao.core.role.entity.add.UserRoleAddDQ;
import com.skrivet.supports.authority.dao.core.role.entity.select.RoleDetailDP;
import com.skrivet.supports.authority.dao.core.role.entity.select.RoleListDP;
import com.skrivet.supports.authority.dao.core.role.entity.update.RoleUpdateDQ;
import com.skrivet.supports.authority.dao.mongodb.role.template.RoleTemplate;
import com.skrivet.supports.authority.dao.mongodb.role.template.UserRoleTemplate;
import com.skrivet.supports.authority.dao.mongodb.role.template.update.RoleUpdateTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Dao
public class RoleDao extends MongoDao implements com.skrivet.supports.authority.dao.core.role.RoleDao {
    public static final String COLLECTION_NAME = "skrivet_role";

    @Override
    public void insert(RoleAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, RoleTemplate.class));
    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("id").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    @Override
    public Long update(RoleUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, RoleUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    @Override
    public RoleDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), RoleTemplate.class),
                RoleDetailDP.class);
    }

    @Override
    public RoleDetailDP selectOneByValue(String value) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("value").is(value)), RoleTemplate.class),
                RoleDetailDP.class);
    }

    @Override
    public List<RoleListDP> selectList() {
        return entityConvert.convertList(mongoTemplate.find(new Query(), RoleTemplate.class), RoleListDP.class);

    }

    @Override
    public List<String> selectUserRoleIds(String userId) {
        List<String> result = new ArrayList<>();
        List<UserRoleTemplate> userRoleTemplates = mongoTemplate.find(new Query(Criteria.where("userId").is(userId)), UserRoleTemplate.class);
        if (!CollectionUtils.isEmpty(userRoleTemplates)) {
            userRoleTemplates.forEach(userRoleTemplate -> result.add(userRoleTemplate.getRoleId()));
        }
        return result;
    }

    @Override
    public Long deleteUserRolesByUserId(String userId) {
        return mongoTemplate.remove(
                new Query(Criteria.where("userId").is(userId)),
                "skrivet_user_role")
                .getDeletedCount();
    }

    @Override
    public void insertUserRoles(UserRoleAddDQ condition) {
        if (!CollectionUtils.isEmpty(condition.getRoles())) {
            condition.getRoles().forEach(role -> {
                UserRoleTemplate userRoleTemplate = new UserRoleTemplate();
                userRoleTemplate.setRoleId(role.getRoleId());
                userRoleTemplate.setUserId(condition.getUserId());
                mongoTemplate.insert(userRoleTemplate);
            });
        }
    }
}
