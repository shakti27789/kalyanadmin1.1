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
@Table(name = "t_appdetail")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class appBean {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "appname")
	private String appname;

	@Column(name = "appurl")
	private String appurl;

	@Column(name = "loginimage")
	private String loginimage;

	@Column(name = "loginimagemob")
	private String loginimagemob;

	@Column(name = "active")
	private Boolean active;

	@Column(name = "createdon")
	private Date createdon;

	@Column(name = "updatedon")
	private Date updatedon;

	@Column(name = "isMultiple")
	private Boolean isMultiple;

	@Column(name = "appid")
	private Integer appId;
	

	@Column(name = "hisabkitabtype")
	private Integer hisabkitabtype;

}
