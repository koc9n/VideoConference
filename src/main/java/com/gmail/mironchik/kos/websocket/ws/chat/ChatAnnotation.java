package com.gmail.mironchik.kos.websocket.ws.chat;

import com.gmail.mironchik.kos.web.model.EventType;
import com.gmail.mironchik.kos.web.model.Member;
import com.gmail.mironchik.kos.web.model.Message;
import com.gmail.mironchik.kos.web.model.TransferData;
import com.gmail.mironchik.kos.web.services.MessageRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: cmironchik
 * Date: 11.07.13
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
@ServerEndpoint(value = "/ws/chat")
public class ChatAnnotation {

    MessageRepository messageRepository = getMessageRepository();

    private MessageRepository getMessageRepository() {
        ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
        return (MessageRepository) ctx.getBean("messageRepository");
    }

    static Logger log = LoggerFactory.getLogger(ChatAnnotation.class);

    private static final Map<String, Set<Session>> connectedUsers =
            new HashMap();
    public static final String NICK = "screen_name";
    public static final String PHOTO_URL = "photo_200_orig";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";

    private static ObjectMapper mapper = new ObjectMapper();

    private String nickname;
    private Session session;

    @OnOpen
    public void start(Session session) {
        this.nickname = getParamFromRequest(session, NICK);
        this.session = session;
        if (!connectedUsers.keySet().contains(nickname)) {
            connectedUsers.put(nickname, new HashSet<>());
        }
        connectedUsers.get(nickname).add(session);

        TransferData transferData = new TransferData(EventType.USER_CONNECTED);
        transferData.setMember(obtainMember(session, nickname));
        broadcast(writeData(transferData));
    }

    private static String writeData(TransferData transferData) {
        String message = null;
        try {
            message = mapper.writeValueAsString(transferData);
        } catch (IOException e) {
            log.error("error during converting object to JSON string", e);
        }
        return message;
    }

    private static Member obtainMember(Session session, String nickname) {
        return new Member(nickname,
                getParamFromRequest(session, FIRST_NAME),
                getParamFromRequest(session, LAST_NAME),
                getParamFromRequest(session, PHOTO_URL));
    }

    private static String getParamFromRequest(Session session, String paramName) {
        return session.getRequestParameterMap().get(paramName).get(0);
    }


    @OnClose
    public void end() {

        TransferData transferData = new TransferData(EventType.USER_DISCONNECTED);
        transferData.setMember(obtainMember(session, nickname));

        connectedUsers.get(nickname).remove(session);

        broadcast(writeData(transferData));
    }


    @OnMessage
    public void incoming(String message) {
        // Never trust the client
        Message msg = null;
        try {
            msg = mapper.readValue(message, Message.class);
        } catch (IOException e) {
            log.error("error during read object from JSON string", e);
        }
        TransferData transferData = new TransferData(EventType.MESSAGE);
        transferData.setMessage(msg);

        messageRepository.save(msg);

        broadcast(writeData(transferData));
    }


    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("Chat Error: " + t.toString(), t);
    }


    private static void broadcast(String msg) {
        for (String nickname : connectedUsers.keySet()) {
            sendMessageByNick(msg, nickname);
        }
    }

    private static void sendMessageByNick(String msg, String nickname) {
        Set<Session> sessions = connectedUsers.get(nickname);
        for (Session session : sessions) {
            try {
                synchronized (session) {
                    session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                log.info("Chat Error: Failed to send message to client", e);

                TransferData transferData = new TransferData(EventType.USER_DISCONNECTED);
                transferData.setMember(obtainMember(session, nickname));

                connectedUsers.remove(nickname);
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                broadcast(writeData(transferData));
            }
        }
    }
}
