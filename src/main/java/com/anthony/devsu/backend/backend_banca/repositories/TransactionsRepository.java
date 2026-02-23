package com.anthony.devsu.backend.backend_banca.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anthony.devsu.backend.backend_banca.entities.Transactions;
  
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
@Query("SELECT t FROM Transactions t " +
           "JOIN t.account a " +
           "JOIN a.customer c " +
           "WHERE c.id = :customerId " +
           "AND t.date BETWEEN :inicio AND :fin order by t.date desc")
    List<Transactions> findByClienteAndFecha(
            @Param("customerId") Long customerId, 
            @Param("inicio") LocalDateTime inicio, 
            @Param("fin") LocalDateTime fin);

}
