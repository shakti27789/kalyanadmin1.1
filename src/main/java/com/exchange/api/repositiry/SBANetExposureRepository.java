package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.SBANetExposure;

public interface SBANetExposureRepository extends JpaRepository<SBANetExposure, Integer> {
	
	SBANetExposure findByEventidAndUserid(Integer eventid, Integer userid);
	
	void deleteByEventidAndUserid(Integer eventid, Integer userid);

}
