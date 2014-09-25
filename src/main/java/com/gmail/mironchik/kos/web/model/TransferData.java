package com.gmail.mironchik.kos.web.model;

/**
 * Created by k.mironchik on 9/16/2014.
 */
public class TransferData {
    private EventType eventType;
    private Member member;
    private Message message;

    public TransferData(EventType eventType) {
        this.eventType = eventType;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
