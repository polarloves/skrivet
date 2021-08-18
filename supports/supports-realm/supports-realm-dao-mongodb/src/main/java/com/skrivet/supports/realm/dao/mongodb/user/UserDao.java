package com.skrivet.supports.realm.dao.mongodb.user;

import com.skrivet.plugins.database.core.annotations.Dao;
import com.skrivet.plugins.database.mongodb.dao.MongoDao;
import com.skrivet.supports.realm.dao.core.user.entity.add.UserAddDQ;
import com.skrivet.supports.realm.dao.core.user.entity.select.SecurityUserDP;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserDetailDP;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserListDP;
import com.skrivet.supports.realm.dao.core.user.entity.select.UserSelectPageDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.PasswordUpdateDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.UserUpdateDQ;
import com.skrivet.supports.realm.dao.core.user.entity.update.UserUpdateMineDQ;
import com.skrivet.supports.realm.dao.mongodb.user.template.UserTemplate;
import com.skrivet.supports.realm.dao.mongodb.user.template.select.UserSelectPageTemplate;
import com.skrivet.supports.realm.dao.mongodb.user.template.update.UserUpdateMineTemplate;
import com.skrivet.supports.realm.dao.mongodb.user.template.update.UserUpdateTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Dao
public class UserDao extends MongoDao implements com.skrivet.supports.realm.dao.core.user.UserDao {
    public static final String COLLECTION_NAME = "skrivet_user";

    @Override
    public void insert(UserAddDQ entity) {
        entity.setId(null);
        UserTemplate userTemplate = entityConvert.convert(entity, UserTemplate.class);
        userTemplate.setState(1);
        userTemplate.setLoginCount(0);
        mongoTemplate.insert(userTemplate);
    }

    @Override
    public Long deleteById(String id) {
        return mongoTemplate.remove(new Query(Criteria.where("id").is(id)), COLLECTION_NAME).getDeletedCount();
    }

    @Override
    public Long update(UserUpdateDQ entity) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, UserUpdateTemplate.class), COLLECTION_NAME).getMatchedCount();
    }

    @Override
    public Long updateMine(UserUpdateMineDQ entity) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("id").is(entity.getId())), buildUpdate(entity, UserUpdateMineTemplate.class), COLLECTION_NAME).getMatchedCount();
    }

    @Override
    public UserDetailDP selectOneById(String id) {
        return entityConvert.convertIgnoreFiledError(mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), UserTemplate.class), UserDetailDP.class);
    }

    @Override
    public List<UserListDP> selectPageList(UserSelectPageDQ condition) {
        return entityConvert.convertListIgnoreFiledError(mongoTemplate.find(
                buildQuery(condition, UserSelectPageTemplate.class, condition.getSort(), condition.getOrder())
                        .skip(condition.getPageStartNumber())
                        .limit(condition.getPageOffsetNumber()),
                UserTemplate.class, COLLECTION_NAME), UserListDP.class);
    }

    @Override
    public Long selectPageCount(UserSelectPageDQ condition) {
        return mongoTemplate.count(buildQuery(condition, UserSelectPageTemplate.class), UserTemplate.class, COLLECTION_NAME);
    }

    @Override
    public SecurityUserDP selectSecurityUserByUserName(String userName) {
        UserTemplate entity = mongoTemplate.findOne(new Query(Criteria.where("userName").is(userName)), UserTemplate.class);
        SecurityUserDP result = null;
        if (entity != null) {
            result=new SecurityUserDP();
            result.setId(entity.getId());
            result.setAccount(entity.getUserName());
            result.setDisableReason(entity.getDisableReason());
            result.setPassword(entity.getPassword());
            result.setSalt(entity.getSalt());
            result.setState(entity.getState());
        }
        return result;

    }

    @Override
    public Long login(String userName, String ip, String lastLoginDate) {
        UserTemplate entity=mongoTemplate.findOne(new Query(Criteria.where("userName").is(userName)), UserTemplate.class);
        return mongoTemplate.updateMulti(new Query(Criteria.where("userName").is(userName)),  new Update().set("lastLoginIp",ip).set("lastLoginDate",lastLoginDate).set("loginCount",entity.getLoginCount()+1), COLLECTION_NAME).getMatchedCount();

    }

    @Override
    public Long updatePassword(PasswordUpdateDQ condition) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("id").is(condition.getId())),  new Update().set("password",condition.getPassword()).set("salt",condition.getSalt()), COLLECTION_NAME).getMatchedCount();
    }

    @Override
    public Long enable(String id) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("id").is(id)),  new Update().set("state",1), COLLECTION_NAME).getMatchedCount();
    }

    @Override
    public Long disable(String id, String reason) {
        return mongoTemplate.updateMulti(new Query(Criteria.where("id").is(id)),  new Update().set("state",0).set("disableReason",reason), COLLECTION_NAME).getMatchedCount();
    }
}
