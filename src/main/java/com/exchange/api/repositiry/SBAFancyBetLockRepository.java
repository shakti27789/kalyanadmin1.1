package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.SBABetLock;
import com.exchange.api.bean.SBAFancyBetLock;

public interface SBAFancyBetLockRepository extends JpaRepository<SBAFancyBetLock, Integer> {
	
	SBAFancyBetLock findByEventidAndUserid(Integer eventid, Integer userId);
	
	void deleteByEventidAndUserid(Integer eventid, Integer userId);

}
