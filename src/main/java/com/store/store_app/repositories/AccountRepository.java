package com.store.store_app.repositories;

import com.store.store_app.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    public List<Account> findAllAccountByUserId(Long userId);

}
