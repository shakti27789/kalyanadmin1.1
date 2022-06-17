package com.exchange.api.repositiry;



import org.springframework.data.jpa.repository.JpaRepository;

import com.exchange.api.bean.UserIp;

public interface UserIpRepository extends JpaRepository<UserIp,String>{

	public UserIp findByuserid(String userid);
}
