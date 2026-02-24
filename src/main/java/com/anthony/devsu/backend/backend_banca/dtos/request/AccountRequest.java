package com.anthony.devsu.backend.backend_banca.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountRequest {

    @NotBlank(message = "El número de cuenta es obligatorio")
    @Pattern(regexp = "^[0-9]+$", message = "El número de cuenta solo debe contener números")
    private String number;

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "^(Ahorros|Corriente)$", message = "El tipo de cuenta debe ser 'Ahorros' o 'Corriente'")
    private String type;

    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private Double balance;

    private Boolean status = true;  

    @NotBlank(message = "La identificación del cliente es obligatoria")
    private String customerIdentification;

    
}