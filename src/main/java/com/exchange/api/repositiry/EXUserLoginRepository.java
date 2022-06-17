package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import com.exchange.api.bean.EXUserLogin;


public interface EXUserLoginRepository  extends JpaRepository<EXUserLogin,String> {

	public EXUserLogin findByUserid(String userid);
	
	@Transactional
	@Modifying
	public long deleteByUserid(String id);
	
	public EXUserLogin findByUseridAndSessionid(String userid,String sessionid);
	
	public EXUserLogin findByUseridAndLoginIp(String userid,String ip);
	
	@Transactional
	@Modifying
	public long deleteByUseridAndLoginIp(String userId,String Ip);
	
	public EXUserLogin findByUseridAndSessionidAndLoginIp(String sessionid,String userid,String Ip);
	
}
