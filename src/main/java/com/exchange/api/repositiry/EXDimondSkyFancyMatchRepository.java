package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.EXDimondSkyFancyMatch;

public interface EXDimondSkyFancyMatchRepository extends JpaRepository<EXDimondSkyFancyMatch, String> {

	public EXDimondSkyFancyMatch findByDaimondfancyid(String fancyid);
	
	public EXDimondSkyFancyMatch findBySkyfancyid(String fancyid);
	
	public EXDimondSkyFancyMatch findByDaimondfancyidAndSkyfancyid(String daimondid, String fancyid);
}
