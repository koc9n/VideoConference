package com.gmail.mironchik.kos.web.dao;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created by kos on 13.02.2015.
 */
public class Member extends MongoDocument {
    @Indexed
    private String vkId;
    @Indexed
    private String fName;
    @Indexed
    private String lName;
    private String photoUrl;
    private String photoThumb;

}
