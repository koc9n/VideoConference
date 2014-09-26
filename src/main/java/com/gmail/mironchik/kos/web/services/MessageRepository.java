package com.gmail.mironchik.kos.web.services;

import com.gmail.mironchik.kos.web.model.Member;
import com.gmail.mironchik.kos.web.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by k.mironchik on 9/25/2014.
 */
@Repository
public class MessageRepository  {
    @Autowired
    private MongoOperations mongoOperations;

    public List<Message> findAll(){
        return mongoOperations.findAll(Message.class);
    }

    public void save (Message message) {
        mongoOperations.save(message);
    }


}
