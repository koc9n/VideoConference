package com.gmail.mironchik.kos.websocket.ws.chat;

import com.gmail.mironchik.kos.web.dto.EventType;
import com.gmail.mironchik.kos.web.dto.Member;
import com.gmail.mironchik.kos.web.dto.Message;
import com.gmail.mironchik.kos.web.dto.TransferData;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    private static final Log log = LogFactory.getLog(ChatAnnotation.class);

    private static final Map<String, Session> connectedUsers =
            new HashMap();
    private static final Map<String, Integer> repeatedUsers =
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
        this.session = session;
        String nick = getParamFromRequest(session, NICK);
        if (repeatedUsers.keySet().contains(nick)) {
            repeatedUsers.put(nick, repeatedUsers.get(nick) + 1);
            nick += "." + repeatedUsers.get(nick);
        } else {
            repeatedUsers.put(nick, 1);
        }
        connectedUsers.put(nick, session);

        this.nickname = nick;

        TransferData transferData = new TransferData(EventType.USER_CONNECTED);
        transferData.setMember(obtainMember(session, nickname));
        broadcast(writeData(transferData));
    }

    private static String writeData(TransferData transferData) {
        String message = null;
        try {
            message = mapper.writeValueAsString(transferData);
        } catch (IOException e) {
            log.error(e);
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
        transferData.setMember(obtainMember(connectedUsers.get(nickname), nickname));

        connectedUsers.remove(nickname);

        broadcast(writeData(transferData));
    }


    @OnMessage
    public void incoming(String message) {
        // Never trust the client
        Message msg = null;
        try {
            msg = mapper.readValue(message, Message.class);
        } catch (IOException e) {
            log.error(e);
        }
        TransferData transferData = new TransferData(EventType.MESSAGE);
        transferData.setMessage(msg);

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
        Session session = connectedUsers.get(nickname);
        try {
            synchronized (session) {
                session.getBasicRemote().sendText(msg);
            }
        } catch (IOException e) {
            log.info("Chat Error: Failed to send message to client", e);

            TransferData transferData = new TransferData(EventType.USER_DISCONNECTED);
            transferData.setMember(obtainMember(connectedUsers.get(nickname), nickname));

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
