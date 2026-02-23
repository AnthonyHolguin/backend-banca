package com.anthony.devsu.backend.backend_banca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired 
    private CustomerRepository customerRepo;
    

    @Transactional
    public Customer save(Customer customer) {
        // Validar que no se repita la identificación
        if (customerRepo.existsByIdentify(customer.getIdentify())) {
            throw new RuntimeException("La identificación ya se encuentra registrada");
        }
        return customerRepo.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public Optional<Customer> findById(Long id) {
        return customerRepo.findById(id);
    }

    public void deleteById(Long id){
        customerRepo.deleteById(id);
    }
}