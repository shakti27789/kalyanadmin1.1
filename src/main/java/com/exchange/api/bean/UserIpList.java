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
@Table(name = "t_useriplist")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserIpList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "userid")
	private String userid;

	@Column(name = "ipdetail")
	private String ipdetail;

	@Column(name = "lastlogin")
	private String lastlogin;

	@Column(name = "logintime")
	private String logintime;

	@Column(name = "lastlogout")
	private String lastlogout;

	@Column(name = "logout")
	private Boolean logout;

	@Column(name = "logoutby")
	private String logoutby;

	@Column(name = "logoutduration")
	private Date logoutduration;

	@Column(name = "loggedin")
	private Boolean loggedin;

	@Column(name = "oldsession")
	private String oldsession;

	@Column(name = "newsesion")
	private String newsesion;

	@Column(name = "parentid")
	private String parentid;

	@Column(name = "username")
	private String username;
}
