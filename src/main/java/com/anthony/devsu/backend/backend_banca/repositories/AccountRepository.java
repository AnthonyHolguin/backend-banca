package com.anthony.devsu.backend.backend_banca.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; 

import com.anthony.devsu.backend.backend_banca.entities.Account; 
 
public interface AccountRepository  extends JpaRepository<Account, Long>{
    boolean existsByNumber(String number);
    
    Optional<Account> findByNumber(String number);
}
