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
@Table(name = "t_sport")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Sport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "name")
	String name;

	@Column(name = "sportid")
	Integer sportId;

	@Column(name = "marketcount")
	String marketCount;

	@Column(name = "createdon")
	String createdOn;

	@Column(name = "updatedon")
	String updatedOn;

	@Column(name = "status")
	Boolean status;

	@Column(name = "adminid")
	String adminid;

	@Column(name = "isnew")
	Boolean isNew;

	@Column(name = "type")
	String type;
	
	@Column(name = "betlock")
	String betlock;
	
	@Column(name = "ismysqlupdated")
	Boolean ismysqlupdated;
}
