package com.gmail.mironchik.kos.web.model;

import com.gmail.mironchik.kos.web.dao.MongoDocument;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

/**
 * Created by k.mironchik on 9/15/2014.
 */


public class Message {
    private String sender;
    private String text;
    private List<String> recipients;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }
}
