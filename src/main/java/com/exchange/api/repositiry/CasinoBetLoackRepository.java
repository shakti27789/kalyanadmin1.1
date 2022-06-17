package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.CasinoBetLoack;

@Repository
public interface CasinoBetLoackRepository extends JpaRepository<CasinoBetLoack,Integer> {

	public CasinoBetLoack findByUseridAndTabletype(Integer userid ,String tabletype);
	
	public CasinoBetLoack findByUserid(Integer userid);
}
		