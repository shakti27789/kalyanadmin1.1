package com.exchange.api.controller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

	
	private static Map<String,WebSocketSession> userIdSessionMap = new ConcurrentHashMap();
	private static Map<WebSocketSession,String> sessionUserIdMap = new ConcurrentHashMap<>();


	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message)
			throws Exception {

		String payload = message.getPayload();
		JSONObject jsonObject = new JSONObject(payload);
		
	}
	
	
	public  static Map<String,WebSocketSession> getUserIdSessionMap(){
	    return userIdSessionMap;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
	    super.afterConnectionEstablished(session);
	}
}
