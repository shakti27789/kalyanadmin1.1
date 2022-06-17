package com.exchange.api.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TeligramBot extends TelegramLongPollingBot {

	@Override
	public void onUpdateReceived(Update update) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "kalyankhelo_bot";
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "5381892559:AAFWnIbC8BKpGquUnStM72exxV-ydMvVt7E";
	}

}
