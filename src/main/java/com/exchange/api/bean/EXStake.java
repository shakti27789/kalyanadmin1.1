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
@Table(name = "t_stack")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EXStake {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@Column(name = "stack1")
	private Integer stack1 = 0;

	@Column(name = "stack2")
	private Integer stack2 = 0;

	@Column(name = "stack3")
	private Integer stack3 = 0;

	@Column(name = "stack4")
	private Integer stack4 = 0;

	@Column(name = "stack5")
	private Integer stack5 = 0;

	@Column(name = "stack6")
	private Integer stack6 = 0;

	@Column(name = "stack7")
	private Integer stack7 = 0;

	@Column(name = "stack8")
	private Integer stack8 = 0;

	@Column(name = "stack9")
	private Integer stack9 = 0;

	@Column(name = "stack10")
	private Integer stack10 = 0;
	
	@Column(name = "userid")
	private String userid ;

}
