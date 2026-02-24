package com.anthony.devsu.backend.backend_banca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthony.devsu.backend.backend_banca.dtos.request.AccountRequest;
import com.anthony.devsu.backend.backend_banca.dtos.response.AccountResponse;
import com.anthony.devsu.backend.backend_banca.entities.Account;
import com.anthony.devsu.backend.backend_banca.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccountRequest account) {
        try {
            AccountResponse created = accountService.save(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            // Retorna un 400 Bad Request con el mensaje de "Usuario inactivo"
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<AccountResponse> getAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
        return accountService.findById(id)
                .map(ResponseEntity::ok)  
                .orElse(ResponseEntity.notFound().build()); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AccountRequest details) {
        try {
            AccountResponse updatedAccount = accountService.update(id, details);
            return ResponseEntity.ok(updatedAccount);
        } catch (RuntimeException e) {
            // Retorna 404 si la cuenta no existe o 400 si hay un error de validación
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
