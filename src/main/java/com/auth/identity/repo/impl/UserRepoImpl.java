package com.auth.identity.repo.impl;

import com.auth.identity.domain.AppUser;
import com.auth.identity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl implements UserRepo
{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public AppUser findByUsername(String username)
    {
        Query query = new Query(Criteria.where("username").is(username));
        return (AppUser) mongoTemplate.find(query, AppUser.class).get(0);
    }

    @Override
    public AppUser findByEmail(String email) {
        Query query = new Query(Criteria.where("email").is(email));
        return (AppUser) mongoTemplate.find(query, AppUser.class);
    }

    @Override
    public AppUser save(AppUser appUser)
    {
        AppUser savedUser = mongoTemplate.save(appUser);
        return savedUser;
    }

    @Override
    public List<AppUser> findAll()
    {
        return mongoTemplate.findAll(AppUser.class);
    }
}
