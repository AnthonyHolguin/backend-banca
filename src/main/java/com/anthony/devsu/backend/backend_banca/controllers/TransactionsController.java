package com.anthony.devsu.backend.backend_banca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthony.devsu.backend.backend_banca.dtos.TransactionsRequest;
import com.anthony.devsu.backend.backend_banca.exceptions.InsufficientBalanceException;
import com.anthony.devsu.backend.backend_banca.services.TransactionsService;
@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    @Autowired private TransactionsService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionsRequest trans) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarMovimiento(trans));
        } catch (InsufficientBalanceException e) {
            // F3: Captura de error personalizada
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
