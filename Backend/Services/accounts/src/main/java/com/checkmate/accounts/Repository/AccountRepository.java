package com.checkmate.accounts.Repository;

import com.checkmate.accounts.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository extends JpaRepository<Account, Integer> {
    
}
