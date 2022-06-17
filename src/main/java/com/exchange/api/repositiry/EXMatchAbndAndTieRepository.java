package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXMatchAbndAndTie;

@Repository
public interface EXMatchAbndAndTieRepository extends JpaRepository<EXMatchAbndAndTie, String> {

	public EXMatchAbndAndTie findByMarketid(String marketid);
	
	public EXMatchAbndAndTie findByMatchid(Integer matchid);
	
	long deleteByMarketid(String deleteid);
}
