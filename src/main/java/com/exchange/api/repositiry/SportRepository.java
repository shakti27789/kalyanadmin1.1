package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.Sport;

public interface SportRepository extends  JpaRepository<Sport, String>{
	
	public Sport findBysportIdAndAdminid(Integer sportId,String subadminid);
	
	public Sport findBySportIdAndAdminid(Integer sportId,String adminid);
	
	public ArrayList<Sport> findBystatusAndAdminid (Boolean status,String subadminid);	
	
	public Sport findBySportId(Integer sportid);
	
	public ArrayList<Sport> findByStatus(Boolean status);
	
	
	public ArrayList<Sport> findByType(String type);
	
	public ArrayList<Sport> findByTypeAndStatus(String type,Boolean status);
	
	public Sport findBySportIdAndType(Integer sportId, String type);
	
	public ArrayList<Sport> findByStatusAndType(Boolean status,String type);
	
	
}
