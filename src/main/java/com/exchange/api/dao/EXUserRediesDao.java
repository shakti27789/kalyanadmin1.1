	package com.exchange.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXUser;


@Repository
public class EXUserRediesDao {

	
	@Autowired
	private RedisTemplate  template;
	
	public static final String  Hash_Key="User";
	
	public EXUser saveByUserId(EXUser user) {
		template.opsForHash().put(Hash_Key, user.getUserid(), user);
		return user;
	}
	
	
	public EXUser saveById(EXUser user) {
		template.opsForHash().put(Hash_Key, user.getId(), user);
		return user;
	}
	
	public EXUser findByUserId(String id){
		return (EXUser) template.opsForHash().get(Hash_Key, id);
	}
	
	public EXUser findById(Integer id){
		return (EXUser) template.opsForHash().get(Hash_Key, id);
	}
	
	public String deleteUserById(String userid) {
		template.opsForHash().delete(Hash_Key, userid);
		return "deleted";
	}
}
