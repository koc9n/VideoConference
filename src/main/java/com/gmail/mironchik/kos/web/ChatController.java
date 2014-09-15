package com.gmail.mironchik.kos.web;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by k.mironchik on 9/15/2014.
 */
@Controller("/chat")
public class ChatController {

    @RequestMapping(value = "/login/{nick}", method = RequestMethod.GET)
    @ResponseBody
    public Object login(){
        return null;
    }
}
