package com.exchange.api.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Document(collection = "event_viewer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXMatchViewer {

	
	@Id
	public String id;

	private Integer matchId;
	
	private String appName;
	
	
}
