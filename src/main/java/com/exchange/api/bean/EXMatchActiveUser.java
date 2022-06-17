package com.exchange.api.bean;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "t_match_active_user")
public class EXMatchActiveUser {


	@Id
	public String id;

	private Integer matchid;
	
	private String userid;
	
}
