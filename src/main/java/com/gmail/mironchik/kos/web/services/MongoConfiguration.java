package com.gmail.mironchik.kos.web.services;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by k.mironchik on 9/25/2014.
 */
@ComponentScan
@EnableMongoRepositories
public class MongoConfiguration extends AbstractMongoConfiguration {

    public static final String CHAT_DB = "chatDB";

    @Override
    protected String getDatabaseName() {
        return CHAT_DB;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient mongoClient = new MongoClient("localhost");
        mongoClient.setWriteConcern(WriteConcern.SAFE);
        return mongoClient;
    }
}
