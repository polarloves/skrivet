package com.skrivet.supports.realm.dao.mongodb.dept;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.realm.dao.core.dept.entity.add.DeptAddDQ;
import com.skrivet.supports.realm.dao.core.dept.entity.select.DeptDetailDP;
import com.skrivet.supports.realm.dao.core.dept.entity.select.DeptListDP;
import com.skrivet.supports.realm.dao.core.dept.entity.update.DeptUpdateDQ;
import com.skrivet.supports.realm.dao.mongodb.dept.template.DeptTemplate;
import com.skrivet.supports.realm.dao.mongodb.dept.template.update.DeptUpdateTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

@Dao
public class DeptDao extends MongoDao implements com.skrivet.supports.realm.dao.core.dept.DeptDao {
    public static final String COLLECTION_NAME = "skrivet_dept";

    @Override
    public void insert(DeptAddDQ entity) {
        entity.setId(null);
        mongoTemplate.insert(entityConvert.convert(entity, DeptTemplate.class));
    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(
                new Query(Criteria.where("id").is(id)),
                COLLECTION_NAME)
                .getDeletedCount();
    }

    @Override
    public Long update(DeptUpdateDQ entity) {
        return mongoTemplate.updateMulti(
                new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, DeptUpdateTemplate.class),
                COLLECTION_NAME)
                .getMatchedCount();
    }

    @Override
    public DeptDetailDP selectOneById(String id) {
        return entityConvert.convert(
                mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), DeptTemplate.class),
                DeptDetailDP.class);
    }

    @Override
    public List<DeptListDP> selectList() {
        return entityConvert.convertList(mongoTemplate.find(new Query(), DeptTemplate.class), DeptListDP.class);
    }
}
