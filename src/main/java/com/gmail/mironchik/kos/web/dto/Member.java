package com.gmail.mironchik.kos.web.dto;

/**
 * Created by k.mironchik on 9/16/2014.
 */
public class Member {

    private String nick;
    private String firstName;
    private String lastName;
    private String photoUrl;

    public Member(String nick, String firstName, String lastName, String photoUrl) {
        this.nick = nick;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
