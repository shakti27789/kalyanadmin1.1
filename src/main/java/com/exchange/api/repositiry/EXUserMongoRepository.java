package com.exchange.api.repositiry;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.exchange.api.bean.EXUserMongo;

@Repository
public interface EXUserMongoRepository extends MongoRepository<EXUserMongo, String> {

}
