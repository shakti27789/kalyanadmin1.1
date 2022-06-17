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
@Table(name = "t_series")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Series {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "seriesid")
	private Integer seriesid;

	@Column(name = "seriesname")
	private String seriesname;

	@Column(name = "marketcount")
	private String marketcount;

	@Column(name = "sportid")
	private Integer sportid;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "createdon")
	private String createdon;

	@Column(name = "updatedon")
	private String updatedon;

	@Column(name = "isactive")
	private Boolean isactive;

	@Column(name = "adminid")
	private String adminid;

	@Column(name = "appid")
	private Integer appid;
}
