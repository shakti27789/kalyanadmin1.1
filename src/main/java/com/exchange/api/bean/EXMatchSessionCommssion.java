package com.exchange.api.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "t_matchsessioncommssion")
public class EXMatchSessionCommssion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Column(name = "commssion")
	private String commssion;

	@Column(name = "type")
	private String type;

}
