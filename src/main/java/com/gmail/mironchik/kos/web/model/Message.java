package com.gmail.mironchik.kos.web.model;

import java.util.List;

/**
 * Created by k.mironchik on 9/15/2014.
 */


public class Message {
    private Member sender;
    private String text;
    private List<String> recipients;

    public Member getSender() {
        return sender;
    }

    public void setSender(Member sender) {
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
