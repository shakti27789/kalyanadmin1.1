package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.SBABetLock;

public interface SBABetLockRepository extends JpaRepository<SBABetLock, Integer> {
	
	SBABetLock findByEventidAndUserid(Integer eventid, Integer userId);
	
	void deleteByEventidAndUserid(Integer eventid, Integer userId);

}
