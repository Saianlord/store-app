package com.store.store_app.services;

import com.store.store_app.models.UserEntity;
import com.store.store_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

//    @Autowired
//    public UserService(UserRepository repo) {
//        this.repo = repo;
//    }

    public List<UserEntity> listAll(){
        return repo.findAll();
    }

    public Optional<UserEntity> getById(Long userId){

        return repo.findById(userId);
    }
    public Optional<UserEntity> findUser(String username){
        return repo.findUser(username);
    }

    public void save(UserEntity user){
        repo.save(user);
    }

    public void saveAll(List<UserEntity> users){
        repo.saveAll(users);
    }

    public void delete(Long userId){
        repo.deleteById(userId);
    }
}
