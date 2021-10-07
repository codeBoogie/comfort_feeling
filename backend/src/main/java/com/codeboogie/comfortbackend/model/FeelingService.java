package com.codeboogie.comfortbackend.model;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeelingService {

    @Autowired
    private MongoTemplate mongoTemplate; //몽고DB 템플릿 불러오기

//    @Autowired
//    private FeelingRepository feelingRepository;

    private List<Feeling> findDatas(String key, String value) {
        Criteria criteria = new Criteria(key);
        criteria.is(value);

        Query query = new Query(criteria);

        return mongoTemplate.find(query, Feeling.class, "feeling");
    }

    public Feeling insert(final Feeling feeling) {
        if(feeling == null) {
            throw new NullPointerException("Data Null");
        }
        return mongoTemplate.insert(feeling);
    }

    public void update(final Feeling feeling) {
        if(feeling == null) {
            throw new NullPointerException("Data Null");
        }
        Criteria criteria = new Criteria("userId");
        criteria.is(feeling.getUserId());

        Query query = new Query();

        Update update = new Update();
        update.set("score", feeling.getScore());
        update.set("publishDate", feeling.getPublishDate());
        update.set("text", feeling.getText());
        update.set("xcoord", feeling.getXcoord());
        update.set("ycoord", feeling.getYcoord());

        mongoTemplate.updateFirst(query, update, "feeling");
    }

    public void remove(String key, String value) {
        Criteria criteria = new Criteria(key);
        criteria.is(value);

        Query query = new Query(criteria);

        mongoTemplate.remove(query, "feeling");

    }


}
