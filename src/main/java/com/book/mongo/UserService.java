package com.book.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2018/12/25.
 */
@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveUser(User user) {
        mongoTemplate.save(user, "test_user");
    }

    public User findUser(Long id) {
        return mongoTemplate.findById(id, User.class);
    }

    public List<User> findUsers(String name, int page, int pageSize) {
        //创建查询对象
        Criteria regex = Criteria.where("name").regex(name);
//                .and("id").regex("1");
        Query query = Query.query(regex);
        query.skip((page - 1) * pageSize);
        query.limit(pageSize);
        List<User> list = mongoTemplate.find(query, User.class);
        return list;
    }
}
