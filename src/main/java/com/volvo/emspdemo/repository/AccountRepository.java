package com.volvo.emspdemo.repository;

import com.volvo.emspdemo.domain.AccountAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountAggregate, Long>{
    AccountAggregate findByEmail(String email);
}
