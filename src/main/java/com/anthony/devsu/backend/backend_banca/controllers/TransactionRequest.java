package com.anthony.devsu.backend.backend_banca.controllers;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public class TransactionRequest {

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransaccionType tipo;

}
