package com.gmail.mironchik.kos.web.controller;

import com.gmail.mironchik.kos.web.model.Message;
import com.gmail.mironchik.kos.web.services.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * Created by k.mironchik on 9/15/2014.
 */
@Controller
public class ChatController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(Model model){

        return "accountSettings";
    }
}
