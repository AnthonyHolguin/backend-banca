package com.anthony.devsu.backend.backend_banca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.anthony.devsu.backend.backend_banca.entities.Customer;

 
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    boolean existsByIdentify(String identify);

    Customer findByIdentify(String identify);

    
    
}
