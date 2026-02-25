package com.anthony.devsu.backend.backend_banca.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anthony.devsu.backend.backend_banca.dtos.request.AccountRequest;
import com.anthony.devsu.backend.backend_banca.dtos.request.CustomerRequest;
import com.anthony.devsu.backend.backend_banca.dtos.response.AccountResponse;
import com.anthony.devsu.backend.backend_banca.dtos.response.CustomerResponse;
import com.anthony.devsu.backend.backend_banca.entities.Account;
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.repositories.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Transactional
    public CustomerResponse save(CustomerRequest customer) {
        // Validar que no se repita la identificación
        if (customerRepo.existsByIdentify(customer.getIdentify())) {
            throw new RuntimeException("La identificación ya se encuentra registrada");
        }
        return this.getResponse(customerRepo.save(this.setAccountEntity(customer)));
    }

    public CustomerResponse update(Long id, CustomerRequest request) { 
        Customer existingCustomer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + id));

       
        if (!existingCustomer.getIdentify().equals(request.getIdentify()) &&
                customerRepo.existsByIdentify(request.getIdentify())) {
            throw new RuntimeException("La nueva identificación ya está registrada por otro usuario");
        }
 
        existingCustomer.setName(request.getName());
        existingCustomer.setGenre(request.getGenre());
        existingCustomer.setAge(request.getAge());
        existingCustomer.setIdentify(request.getIdentify());
        existingCustomer.setAddress(request.getAddress());
        existingCustomer.setPhone(request.getPhone());
        existingCustomer.setPassword(request.getPassword());
        existingCustomer.setStatus(request.getStatus());
 
        return this.getResponse(customerRepo.save(existingCustomer));
    }

    public List<CustomerResponse> findAll() {
        return this.mapListToResponse(customerRepo.findAll());
    }

    public Optional<CustomerResponse> findById(Long id) {
        return customerRepo.findById(id).map(this::getResponse);
    }

    public void deleteById(Long id) {
        customerRepo.deleteById(id);
    }

    public Customer setAccountEntity(CustomerRequest customer) {
        Customer entity = new Customer();

        // Mapeo manual de campos
        entity.setName(customer.getName());
        entity.setAge(customer.getAge());
        entity.setGenre(customer.getGenre());
        entity.setIdentify(customer.getIdentify());
        entity.setAddress(customer.getAddress());
        entity.setPhone(customer.getPhone());
        entity.setPassword(customer.getPassword());
        entity.setStatus(customer.getStatus());
        return entity;
    }

    public CustomerResponse getResponse(Customer customer) {
        CustomerResponse entity = new CustomerResponse();

        // Mapeo manual de campos
        entity.setName(customer.getName());
        entity.setAge(customer.getAge());
        entity.setGenre(customer.getGenre());
        entity.setIdentify(customer.getIdentify());
        entity.setAddress(customer.getAddress());
        entity.setPhone(customer.getPhone());
        entity.setStatus(customer.getStatus());
        return entity;
    }

    public List<CustomerResponse> mapListToResponse(List<Customer> customers) {
        return customers.stream()
                .map(this::getResponse)
                .collect(Collectors.toList());
    }

}