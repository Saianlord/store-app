package com.store.store_app.services;

import com.store.store_app.models.Role;
import com.store.store_app.models.UserEntity;
import com.store.store_app.repositories.RoleRepository;
import com.store.store_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository repo;

    @Autowired
    public RoleService(RoleRepository repo) {
        this.repo = repo;
    }

    public List<Role> listAll(){
        return repo.findAll();
    }

    public Optional<Role> getById(Long roleId){

        return repo.findById(roleId);
    }

}
