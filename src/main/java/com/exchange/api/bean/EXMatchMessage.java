package com.exchange.api.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@Entity
@Table(name = "t_match_message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXMatchMessage {



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "message")
	String message;

	@Column(name = "matchid")
	Integer matchid;
	
	@Column(name = "isactive")
	private Boolean isactive;
	
	@Column(name = "createdon")
	private Date createdon;


	


}
