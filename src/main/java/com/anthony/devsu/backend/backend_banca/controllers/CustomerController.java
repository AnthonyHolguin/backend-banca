package com.anthony.devsu.backend.backend_banca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthony.devsu.backend.backend_banca.dtos.request.CustomerRequest;
import com.anthony.devsu.backend.backend_banca.dtos.response.CustomerResponse;
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public CustomerResponse get(@PathVariable Long id) {
        return customerService.findById(id).orElse(null);
    }

    @PutMapping
    public CustomerResponse update(@RequestBody CustomerRequest c) {
        return customerService.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CustomerRequest customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) { 
        CustomerResponse updatedCustomer = customerService.update(id, request);
        return ResponseEntity.ok(updatedCustomer);
    }

}
