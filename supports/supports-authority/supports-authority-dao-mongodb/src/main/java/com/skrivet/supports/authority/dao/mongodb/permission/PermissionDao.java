package com.skrivet.supports.authority.dao.mongodb.permission;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.authority.dao.core.permission.entity.add.PermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.add.ResourcePermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.add.RolePermissionAddDQ;
import com.skrivet.supports.authority.dao.core.permission.entity.select.PermissionDetailDP;
import com.skrivet.supports.authority.dao.core.permission.entity.select.PermissionListDP;
import com.skrivet.supports.authority.dao.core.permission.entity.update.PermissionUpdateDQ;
import com.skrivet.supports.authority.dao.mongodb.permission.template.PermissionTemplate;
import com.skrivet.supports.authority.dao.mongodb.permission.template.ResourcePermissionTemplate;
import com.skrivet.supports.authority.dao.mongodb.permission.template.RolePermissionTemplate;
import com.skrivet.supports.authority.dao.mongodb.permission.template.update.PermissionUpdateTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Dao
public class PermissionDao  extends MongoDao implements com.skrivet.supports.authority.dao.core.permission.PermissionDao {
    public static final String COLLECTION_NAME = "skrivet_permission";

    @Override
    public void insert(PermissionAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, PermissionTemplate.class));
    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("id").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    @Override
    public Long update(PermissionUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, PermissionUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    @Override
    public PermissionDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), PermissionTemplate.class),
                PermissionDetailDP.class);
    }

    @Override
    public PermissionDetailDP selectOneByValue(String value) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("value").is(value)), PermissionTemplate.class),
                PermissionDetailDP.class);
    }

    @Override
    public List<PermissionListDP> selectList() {
        return entityConvert.convertList(mongoTemplate.find(new Query(), PermissionTemplate.class), PermissionListDP.class);

    }

    @Override
    public List<String> selectRolePermissionIds(String roleId) {
        List<String> result = new ArrayList<>();
        List<RolePermissionTemplate> entities = mongoTemplate.find(new Query(Criteria.where("roleId").is(roleId)), RolePermissionTemplate.class);
        if (!CollectionUtils.isEmpty(entities)) {
            entities.forEach(entity -> result.add(entity.getRoleId()));
        }
        return result;
    }

    @Override
    public Long deleteRolePermissionsByRoleId(String roleId) {
        return mongoTemplate.remove(
                new Query(Criteria.where("roleId").is(roleId)),
                "skrivet_role_permission")
                .getDeletedCount();
    }

    @Override
    public void insertRolePermissions(RolePermissionAddDQ condition) {
        if (!CollectionUtils.isEmpty(condition.getPermissions())) {
            condition.getPermissions().forEach(permission -> {
                RolePermissionTemplate rolePermissionTemplate = new RolePermissionTemplate();
                rolePermissionTemplate.setPermissionId(permission.getPermissionId());
                rolePermissionTemplate.setRoleId(condition.getRoleId());
                mongoTemplate.insert(rolePermissionTemplate);
            });
        }
    }

    @Override
    public List<String> selectResourcePermissionIds(String resourceId) {
        List<String> result = new ArrayList<>();
        List<ResourcePermissionTemplate> entities = mongoTemplate.find(new Query(Criteria.where("resourceId").is(resourceId)), ResourcePermissionTemplate.class);
        if (!CollectionUtils.isEmpty(entities)) {
            entities.forEach(entity -> result.add(entity.getPermissionId()));
        }
        return result;
    }

    @Override
    public Long deleteResourcePermissionByResourceId(String resourceId) {
        return mongoTemplate.remove(
                new Query(Criteria.where("resourceId").is(resourceId)),
                "skrivet_resource_permission")
                .getDeletedCount();
    }

    @Override
    public void insertResourcePermissions(ResourcePermissionAddDQ condition) {
        if (!CollectionUtils.isEmpty(condition.getPermissions())) {
            condition.getPermissions().forEach(permission -> {
                ResourcePermissionTemplate resourcePermissionTemplate = new ResourcePermissionTemplate();
                resourcePermissionTemplate.setPermissionId(permission.getPermissionId());
                resourcePermissionTemplate.setResourceId(condition.getResourceId());
                mongoTemplate.insert(resourcePermissionTemplate);
            });
        }
    }
}
