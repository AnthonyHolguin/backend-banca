package com.anthony.devsu.backend.backend_banca.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anthony.devsu.backend.backend_banca.commons.TransaccionType;
import com.anthony.devsu.backend.backend_banca.entities.Account;
import com.anthony.devsu.backend.backend_banca.entities.Customer;
import com.anthony.devsu.backend.backend_banca.entities.Transactions;
import com.anthony.devsu.backend.backend_banca.repositories.AccountRepository;
import com.anthony.devsu.backend.backend_banca.repositories.CustomerRepository;

import jakarta.transaction.Transactional;
@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private CustomerRepository customerRepo;  

    @Autowired
    private TransactionsService transactionsService;

    public List<Account> findAll() {
        return accountRepo.findAll();
    }

    public Optional<Account> findById(Long id) {
        return accountRepo.findById(id);
    }

    @Transactional
    public Account update(Long id, Account details) {
        return accountRepo.findById(id)
            .map(account -> {
                // Actualización de campos básicos
                account.setNumber(details.getNumber());
                account.setType(details.getType());
                account.setStatus(details.getStatus());
                
                // Si en el update se envía un nuevo cliente, validamos su estado
                if (details.getCustomer() != null && details.getCustomer().getId() != null) {
                    Customer customer = customerRepo.findById(details.getCustomer().getId())
                            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
                    
                    if (!customer.getStatus()) {
                        throw new RuntimeException("No se puede asignar la cuenta a un usuario inactivo");
                    }
                    account.setCustomer(customer);
                }

                return accountRepo.save(account);
            }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }

    @Transactional
    public Account save(Account newAccount) {
        // 1. Verificar si el cliente existe en el JSON/Request
        if (newAccount.getCustomer() == null || newAccount.getCustomer().getId() == null) {
            throw new RuntimeException("La cuenta debe estar asociada a un cliente.");
        }

        // 2. Buscar al cliente en la base de datos
        Customer customer = customerRepo.findById(newAccount.getCustomer().getId())
                .orElseThrow(() -> new RuntimeException("El cliente no existe."));


        // 3. VALIDACIÓN: Verificar si el usuario está inactivo
        // Según tu tabla de casos de uso, el estado es un String "True" o "False"
        if (!customer.getStatus()) {
            throw new RuntimeException("No se puede crear la cuenta: El usuario se encuentra inactivo.");
        }

        Boolean foundAccount = accountRepo.existsByNumber(newAccount.getNumber());
        if(foundAccount){ 
            throw new RuntimeException("No se puede crear la cuenta: Esta cuenta ya existe para otro usuario."); 
        }
       

        // 4. Si está activo, asociamos el objeto completo y guardamos
        newAccount.setCustomer(customer);
        Account savedAccount = accountRepo.save(newAccount);
        if (savedAccount.getBalance() > 0) {
            Transactions initialMove = new Transactions();
            initialMove.setAccount(savedAccount);
            initialMove.setType(TransaccionType.DEPOSITO);
            initialMove.setDescription("Depósito inicial");
            initialMove.setValue(savedAccount.getBalance());
            // El balance de la transacción será el mismo saldo inicial
            initialMove.setDate(LocalDateTime.now());
            initialMove.setBalance(savedAccount.getBalance()); 
            
            // Llamamos al servicio de transacciones para persistirlo
            transactionsService.recordInitialTransaction(initialMove);
        }

        return savedAccount;
    }
}
