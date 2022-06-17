package com.exchange.api.bean;

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
@Table(name = "t_message")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "message")
	String message;

	@Column(name = "date")
	String date;

	@Column(name = "appid")
	Integer appid;

	@Column(name = "msgtype")
	String msgtype;

	@Column(name = "msgid")
	String msgid;

}
