package com.volvo.emspdemo.repository;

import com.volvo.emspdemo.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByEmail(String email);
    Page<Account> findAccountByUpdatedAtAfter(LocalDateTime date, Pageable pageable);
}
