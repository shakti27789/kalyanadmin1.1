
package com.exchange.api.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
	
	Message findByAppid(Integer appid);
	
	public Message findByMsgtypeAndMsgid(String msgtype,String msgid);

}
