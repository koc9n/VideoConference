package com.gmail.mironchik.kos.web.services;

import com.gmail.mironchik.kos.web.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by k.mironchik on 9/25/2014.
 */
public class MessageRepository  {
    @Autowired
    private MongoOperations mongoOperations;
}
