package com.anthony.devsu.backend.backend_banca.requests;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionsRequest {

    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "^(Deposito|Retiro)$", message = "El tipo debe ser 'Deposito' o 'Retiro'")
    @Enumerated(EnumType.STRING)    
    private TransaccionType type;

    private String description;

    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor a cero")
    private Double value;
}
