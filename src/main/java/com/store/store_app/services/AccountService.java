package com.store.store_app.services;

import com.store.store_app.models.Account;
import com.store.store_app.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repo;

    @Autowired
    public AccountService(AccountRepository repo) {
        this.repo = repo;
    }

    public List<Account> findAllAccountByUserId(Long userId){
        return repo.findAllAccountByUserId(userId);
    }


    public Account getById(Long accountId){

        return repo.findById(accountId)
                .orElseThrow(() -> new NoSuchElementException("Account not found with id: " + accountId));
    }

    public void save(Account account){
        repo.save(account);
    }

    public void delete(Long accountId){
        repo.deleteById(accountId);
    }
}
