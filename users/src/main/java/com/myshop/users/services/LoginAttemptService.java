package com.myshop.users.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
@Service
public class LoginAttemptService {
    public static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    public static final int ATTEMPTS_INCREMENT = 1;
    private LoadingCache<String ,Integer> loginAttemptCache;

    public  LoginAttemptService(){
        super();
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                });
    }
    public void evicUserFromLoginAttemptCache(String username){
        loginAttemptCache.invalidate(username);
    }
    public  void addUserToLoginAttemptCache(String username)  {
        int attempts = 0 ;
        try {
            attempts=ATTEMPTS_INCREMENT+loginAttemptCache.get(username);

        }catch (ExecutionException e){
            e.printStackTrace();
        }
        loginAttemptCache.put(username,attempts);
    }
    public boolean hasExceededMaxAttempts(String username)  {
        try {
            return loginAttemptCache.get(username)>=MAXIMUM_NUMBER_OF_ATTEMPTS;
        }catch (ExecutionException e){
            e.printStackTrace();
        }
        return  false;
    }
}
