package com.gmail.mironchik.kos.web.controller;

import com.gmail.mironchik.kos.web.services.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by k.mironchik on 9/15/2014.
 */
@Controller
public class ChatController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("code") String code) {
        return "accountSettings";
    }
}
