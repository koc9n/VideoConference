package com.gmail.mironchik.kos.web.controller;

import com.gmail.mironchik.kos.web.model.Message;
import com.gmail.mironchik.kos.web.services.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * Created by k.mironchik on 9/15/2014.
 */
@Controller
public class ChatController {

    @Autowired
    MessageRepository messageRepository;

    @RequestMapping(value = "/chat/{nick}", method = RequestMethod.GET)
    @ResponseBody
    public Object login(@PathVariable String nick){
        Message message = new Message();
        message.setSender(nick);
        message.setText("hello " + nick);
        message.setRecipients(Collections.EMPTY_LIST);
        messageRepository.save(message);
        return "{success:true}";
    }
}
