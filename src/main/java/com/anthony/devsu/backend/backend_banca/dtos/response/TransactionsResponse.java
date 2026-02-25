package com.anthony.devsu.backend.backend_banca.dtos.response;

import java.time.LocalDateTime;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType; 
import jakarta.persistence.Enumerated; 
import jakarta.validation.constraints.DecimalMax; 
import jakarta.persistence.EnumType; 
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
  
@Getter
@Setter
public class TransactionsResponse { 
    private Long id;

    private LocalDateTime date;

    @NotNull(message = "El tipo de transacción es obligatorio")
    @Enumerated(EnumType.STRING)
    private TransaccionType type;

    private String description;
 
    @DecimalMax(value = "15000.00", message = "El valor ha superado el límite 15000.00.")

    private double value;
 
    @DecimalMax(value = "999999.99", message = "El saldo ha superado el límite 999999.99.")
    private double balance;
 
    private AccountResponse account;

}
