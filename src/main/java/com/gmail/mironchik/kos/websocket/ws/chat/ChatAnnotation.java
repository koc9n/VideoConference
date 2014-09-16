package com.gmail.mironchik.kos.websocket.ws.chat;

import com.gmail.mironchik.kos.web.dto.EventType;
import com.gmail.mironchik.kos.web.dto.Member;
import com.gmail.mironchik.kos.web.dto.Message;
import com.gmail.mironchik.kos.web.dto.TransferData;
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
    public static final String NICK = "screen_name";
    public static final String PHOTO_URL = "photo_200_orig";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";

    ObjectMapper mapper = new ObjectMapper();

    private String nickname;
    private Session session;


    @OnOpen
    public void start(Session session) {
        this.session = session;
        this.nickname = getParamFromRequest(session, NICK);

        connectedUsers.put(nickname, session);

        TransferData transferData = new TransferData(EventType.USER_CONNECTED);
        transferData.setMember(obtainMember(session));
        broadcast(writeData(transferData));
    }

    private String writeData(TransferData transferData) {
        String message = null;
        try {
            message = mapper.writeValueAsString(transferData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    private Member obtainMember(Session session) {
        return new Member(getParamFromRequest(session, NICK),
                getParamFromRequest(session, PHOTO_URL),
                getParamFromRequest(session, FIRST_NAME),
                getParamFromRequest(session, LAST_NAME));
    }

    private String getParamFromRequest(Session session, String paramName) {
        return session.getRequestParameterMap().get(paramName).get(0);
    }


    @OnClose
    public void end() {

        TransferData transferData = new TransferData(EventType.USER_DISCONNECTED);
        transferData.setMember(obtainMember(connectedUsers.get(nickname)));

        connectedUsers.remove(nickname);

        broadcast(writeData(transferData));
    }


    @OnMessage
    public void incoming(String message) {
        // Never trust the client

        String filteredMessage = null;
        try {
            filteredMessage = String.format("%s: %s",
                    nickname, mapper.readValue(message, Message.class).getText()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        broadcast(filteredMessage);
    }


    @OnError
    public void onError(Throwable t) throws Throwable {
        log.error("Chat Error: " + t.toString(), t);
    }


    private static void broadcast(String msg) {
        for (String nickname : connectedUsers.keySet()) {
            Session session = connectedUsers.get(nickname);
            try {
                synchronized (session) {
                    session.getBasicRemote().sendText(msg);
                }
            } catch (IOException e) {
                log.debug("Chat Error: Failed to send message to client", e);
                connectedUsers.remove(nickname);
                try {
                    session.close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s",
                        nickname, "has been disconnected.");
                broadcast(message);
            }
        }
    }
}
