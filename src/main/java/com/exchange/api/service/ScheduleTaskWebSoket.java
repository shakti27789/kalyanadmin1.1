package com.exchange.api.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class ScheduleTaskWebSoket {
	@Scheduled(cron = "* * 0/1 * * ?")
	public void sendMessage(){
		
		//WebSocketSession session
	}
		
}
