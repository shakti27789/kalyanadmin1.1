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
@Table(name = "t_event")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "seriesid")
	private Integer seriesid;

	@Column(name = "sportid")
	private Integer sportid;

	@Column(name = "eventid")
	private Integer eventid;

	@Column(name = "eventname")
	private String eventname;

	@Column(name = "countrycode")
	private String countrycode;

	@Column(name = "timezone")
	private String timezone;

	@Column(name = "openDate")
	private String openDate;

	@Column(name = "marketcount")
	private Integer marketcount;

	@Column(name = "status")
	private Boolean status;

	@Column(name = "isactive")
	private Boolean isactive;

	@Column(name = "createdon")
	private String createdon;

	@Column(name = "updatedon")
	private String updatedon;

	@Column(name = "adminid")
	private String adminid;

	@Column(name = "appid")
	private Integer appid;

	@Column(name = "addautoFancy")
	private Boolean addautoFancy;
	
	@Column(name = "addautoFancyDiamond")
	private Boolean addautoFancyDiamond;

	@Column(name = "minbet")
	private Integer minbet;

	@Column(name = "maxbet")
	private Integer maxbet;

	@Column(name = "betdelay")
	private Integer betdelay;
	

	@Column(name = "betdelayBookmaker")
	private Integer betdelayBookmaker;
	
	@Column(name = "fancyprovider")
	private String fancyprovider;

	@Column(name = "matchstartdate")
	private Date matchstartdate;
	
	@Column(name = "fancypause")
	private Boolean fancypause;

	@Column(name = "minbetfancy")
	private Integer minbetfancy;

	@Column(name = "maxbetfancy")
	private Integer maxbetfancy;

	@Column(name = "betdelayfancy")
	private Integer betdelayfancy;

	@Column(name = "cricexchageid")
	private String cricexchageId;

	@Column(name = "livetv")
	private Boolean liveTv;

	@Column(name = "betlock")
	private Boolean betLock;

	@Column(name = "minbetbookmaker")
	private Integer minbetBookmaker;

	@Column(name = "maxbetbookmaker")
	private Integer maxbetBookmaker;
	
	
	@Column(name = "maxbetbookmaker2")
	private Integer maxbetBookmaker2;
	
	@Column(name = "totalexposure")
	private Integer totalExposure;

	
	@javax.persistence.Transient
	private Integer backdiff;
	
	@javax.persistence.Transient
	private Integer laydiff;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "playermaxbetfancy")
	private Integer playermaxbetfancy;
	
	@Column(name = "ismysqlupdated")
	Boolean ismysqlupdated;
	
	
}
