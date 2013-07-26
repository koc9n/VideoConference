package com.gmail.mironchik.kos.web;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.commons.io.IOUtils;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: cmironchik
 * Date: 11.07.13
 * Time: 17:54
 * To change this template use File | Settings | File Templates.
 */

public class VideoWebSocketServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    private static final String GUEST_PREFIX = "Guest";

    private final AtomicInteger connectionIds = new AtomicInteger(0);
    private final Set<ChatMessageInbound> connections =
            new CopyOnWriteArraySet<ChatMessageInbound>();

    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        return new ChatMessageInbound(connectionIds.incrementAndGet());
    }

    class ChatMessageInbound extends StreamInbound {

        private final String nickname;

        private ChatMessageInbound(int id) {
            this.nickname = GUEST_PREFIX + id;
        }

        @Override
        protected void onOpen(WsOutbound outbound) {
            connections.add(this);
            this.setOutboundByteBufferSize(1024 * 512);
            String message = String.format("* %s %s",
                    nickname, "has joined.");
            broadcast(message);
        }

        @Override
        protected void onClose(int status) {
            connections.remove(this);
            String message = String.format("* %s %s",
                    nickname, "has disconnected.");
            broadcast(message);
        }

        @Override
        protected void onBinaryData(InputStream inputStream) throws IOException {

            for (ChatMessageInbound connection : connections) {
                if (!this.nickname.equals(connection.nickname)) {
                    try {
                        ByteBuffer buffer = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
                        connection.getWsOutbound().writeBinaryMessage(buffer);
                    } catch (IOException ignore) {
                        // Ignore
                    }
                } else {
                    try {
                        ByteBuffer buffer = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
                        connection.getWsOutbound().writeBinaryMessage(buffer);
                    } catch (IOException ignore) {
                        // Ignore
                    }
                }
            }

        }

        @Override
        protected void onTextData(Reader reader) throws IOException {
            String filteredMessage = IOUtils.toString(reader);
            broadcast(filteredMessage);
        }

        private void broadcast(String message) {
            for (ChatMessageInbound connection : connections) {
                try {
                    CharBuffer buffer = CharBuffer.wrap(message);
                    connection.getWsOutbound().writeTextMessage(buffer);
                } catch (IOException ignore) {
                    // Ignore
                }
            }
        }
    }

}