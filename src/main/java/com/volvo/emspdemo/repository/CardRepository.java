package com.volvo.emspdemo.repository;

import com.volvo.emspdemo.domain.CardAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.smartcardio.Card;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardAggregate, Long> {

    List<CardAggregate> findByContractId(String contractId);

    CardAggregate findByRfId(String rfId);
}
