package com.volvo.emspdemo.repository;

import com.volvo.emspdemo.domain.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByAccountId(Long accountId);
    Page<Card> findCardByUpdatedAtAfter(LocalDateTime date, Pageable pageable);
}
