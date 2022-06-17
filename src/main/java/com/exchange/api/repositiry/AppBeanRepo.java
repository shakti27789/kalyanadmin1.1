package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.appBean;

public interface AppBeanRepo extends JpaRepository<appBean, String>{

	public appBean findByid(String id);
	
	public appBean findByAppurl(String appurl);
	
	ArrayList<appBean> findByIsMultipleAndActive(Boolean isMultiple,Boolean active);
	
	public appBean findByid(Integer id);
	
}
