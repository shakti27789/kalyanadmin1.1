package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.CashinoFavour;

@Repository
public interface EXCashinoFavourRepository extends JpaRepository<CashinoFavour, Integer> {
	
	@Modifying
	@Query("UPDATE CashinoFavour cf SET cf.favour = ?3 WHERE cf.matchid = ?1 and cf.type = ?2")
	public Integer findByMatchidAndType(Integer matchid, String type, Boolean favour);

}
