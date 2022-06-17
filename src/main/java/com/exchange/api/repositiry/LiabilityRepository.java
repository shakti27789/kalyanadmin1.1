package com.exchange.api.repositiry;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.ExMatchOddsBet;
import com.exchange.api.bean.UserLiability;

public interface LiabilityRepository extends JpaRepository<UserLiability, String>{

	public ArrayList<UserLiability> findByUseridAndIsactive(String userid, Boolean b);

	public UserLiability findByMarketidAndUserid(String marketid, String userid);

	public ExMatchOddsBet findByid(Integer id);

	public ArrayList<UserLiability> findByUserid(String userid);

	public ArrayList<UserLiability> findByMarketidAndIsactiveAndIsresultsetAndMatchid(String marketid,Boolean IsActive,Boolean IsResultSet,Integer matchId);

	public ArrayList<UserLiability> findByMarketidAndIsactiveAndIsresultset(String marketid, Boolean IsActive, Boolean IsResultSet);

	public ArrayList<UserLiability> findByMatchidAndMarketidAndIsactive(Integer matchid, String fancyid, Boolean b);
	
	public UserLiability findByMarketidAndIsactiveAndIsresultsetAndMatchidAndUserid(String marketid,Boolean IsActive,Boolean IsResultSet,Integer matchId,String userId);

	public ArrayList<UserLiability> findByMarketidAndIsactive(String marketid,  Boolean b);

	public	ArrayList<UserLiability> findByMatchidAndIsactiveAndTypeAndUserid(Integer matchid, Boolean b, String string,
			String userid);

    public	ArrayList<UserLiability> findByMatchidAndTypeAndIsactive(Integer matchid, String fancy, Boolean b);
  
	public UserLiability findByMatchidAndMarketidAndIsactiveAndUserid(Integer matchid, String marketid, Boolean isactive,String userid);
	
	
	public	ArrayList<UserLiability> findBySupermasteridAndTypeAndMatchid(String spertmasterid,String type,Integer matchId);
    

	public ArrayList<UserLiability> findByaddetoFirestoreAndMatchid(Boolean isactive,Integer matchid);

  
	public ArrayList<UserLiability> findByaddetoFirestoreAndIsresultset(Boolean isactive,Boolean isresultset);
	
	public ArrayList<UserLiability> findByaddetoFirestore(Boolean isactive);
	
	
	public ArrayList<UserLiability> findByMatchidAndIsactive(Integer matchid, Boolean b);
	
	public ArrayList<UserLiability> findByIsactive(Boolean isActive);
	
	public ArrayList<UserLiability> findByIsLibilityclearAndIsProfitLossclearAndMarketid(Boolean islibilityclear,Boolean isprofitlossclear,String marketId);
	
}
