package com.exchange.api.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "t_userlogin")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXUserLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@NotNull
	@Column(name = "userid")
	private String userid;

	@NotNull
	@Column(name = "sessionid")
	private String sessionid;

	@NotNull
	@Column(name = "logintime")
	private Date logintime;

	@NotNull
	@Column(name = "loginip")
	private String loginIp;
}
