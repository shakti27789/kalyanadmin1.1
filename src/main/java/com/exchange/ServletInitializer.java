package com.exchange;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.exchange.config.MyHttpSessionListener;

public class ServletInitializer extends SpringBootServletInitializer {

	  @Override
	    public void onStartup(ServletContext servletContext) throws ServletException {
	        // This can be done here or as the last step in the method
	        // Doing it in this order will initialize the Spring
	        // Framework first, doing it as last step will initialize
	        // the Spring Framework after the Servlet configuration is 
	        // established
	        super.onStartup(servletContext);
	        servletContext.addListener(new MyHttpSessionListener());
	        // This will set to use COOKIE only
	        servletContext
	            .setSessionTrackingModes(
	                Collections.singleton(SessionTrackingMode.COOKIE)
	        );
	        // This will prevent any JS on the page from accessing the
	        // cookie - it will only be used/accessed by the HTTP transport
	        // mechanism in use
	        SessionCookieConfig sessionCookieConfig=
	                servletContext.getSessionCookieConfig();
	        sessionCookieConfig.setHttpOnly(true);
	    }

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ExchangeApplication.class);
	}

}
