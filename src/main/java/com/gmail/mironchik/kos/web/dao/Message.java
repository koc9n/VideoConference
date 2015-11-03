package com.gmail.mironchik.kos.web.dao;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;

/**
 * Created by k.mironchik on 9/25/2014.
 */
public class Message extends MongoDocument {
    @Indexed
    private String sender;
    @TextIndexed
    private String text;
}