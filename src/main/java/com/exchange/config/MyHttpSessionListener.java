package com.exchange.config;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MyHttpSessionListener implements HttpSessionListener {

	 /*@Override
	    public void sessionCreated(HttpSessionEvent event) {
	        System.out.println("==== Session is created ====");
	        event.getSession().setMaxInactiveInterval(1*60);
	    }
	 
	    @Override
	    public void sessionDestroyed(HttpSessionEvent event) {
	        System.out.println("==== Session is destroyed ====");
	    }*/
}
