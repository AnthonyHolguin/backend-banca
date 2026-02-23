package com.anthony.devsu.backend.backend_banca;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.anthony.devsu.backend.backend_banca.entities.Account;
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.entities.Transactions;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {
    
    @Override
    public void configureRepositoryRestConfiguration ( RepositoryRestConfiguration config, CorsRegistry cors){
       // config.exposeIdsFor(Person.class);
           config.exposeIdsFor(Customer.class);
           config.exposeIdsFor(Account.class);
           config.exposeIdsFor(Transactions.class);
    }
}
