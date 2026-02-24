package com.anthony.devsu.backend.backend_banca.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {
    private Long id;
    private String number;
    private String type;
    private double balance;
    private Boolean status;
 
    private String customerName;
    private String customerIdentification;
 
    private String message;
}
