package com.stackroute.stockapp.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.stockapp.model.User;

@Repository
public interface UserRepository  extends MongoRepository<User, String>{
    //validate user by emailId and password
    public  Optional<User> findByEmailIdAndPassword(String emailId, String password);

}
