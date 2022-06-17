package com.exchange.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:global_constants.properties")
public class EXGlobalConstantProperties {
	
	@Value("${lotusmatchurl}")
	private String lotusmatchurl;
	
	@Value("${url}")
	private String url;

	@Value("${lotusfancyurl}")
	private String lotusfancyurl;
	
	@Value("${ownfancy}")
	private String ownfancy;
	
	@Value("${developerbetfair}")
	private String developerbetfair;
	
	@Value("${daimondapi}")
	private String daimondapi;

	
	public String getLotusmatchurl() {
		return lotusmatchurl;
	}

	public void setLotusmatchurl(String lotusmatchurl) {
		this.lotusmatchurl = lotusmatchurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLotusfancyurl() {
		return lotusfancyurl;
	}

	public void setLotusfancyurl(String lotusfancyurl) {
		this.lotusfancyurl = lotusfancyurl;
	}

	public String getOwnfancy() {
		return ownfancy;
	}

	public void setOwnfancy(String ownfancy) {
		this.ownfancy = ownfancy;
	}

	public String getDeveloperbetfair() {
		return developerbetfair;
	}

	public void setDeveloperbetfair(String developerbetfair) {
		this.developerbetfair = developerbetfair;
	}

	public String getDaimondapi() {
		return daimondapi;
	}

	public void setDaimondapi(String daimondapi) {
		this.daimondapi = daimondapi;
	}

	
	
}
