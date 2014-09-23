package com.gmail.mironchik.kos.web.dto;

import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

/**
 * Created by k.mironchik on 9/15/2014.
 */

@Controller
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
