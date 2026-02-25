package com.anthony.devsu.backend.backend_banca.dtos.response;
 
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private Long id;

    private String name;

    private String genre;
 
    private int age;

    private String identify;

    private String address;

    
    private String phone;
    
    private Boolean status;

}
