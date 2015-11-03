package com.gmail.mironchik.kos.web.dao;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by k.mironchik on 9/25/2014.
 */
@Document
public class MongoDocument {

    @Id
    private ObjectId id;
}
