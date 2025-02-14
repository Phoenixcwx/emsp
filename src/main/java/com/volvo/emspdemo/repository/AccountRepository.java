package com.volvo.emspdemo.repository;

import com.volvo.emspdemo.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    @EntityGraph(attributePaths = "cards")
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findByIdWithCards(@Param("id") Long id);
    Account findByEmail(String email);
    Page<Account> findAccountByUpdatedAtAfter(LocalDateTime date, Pageable pageable);
}
