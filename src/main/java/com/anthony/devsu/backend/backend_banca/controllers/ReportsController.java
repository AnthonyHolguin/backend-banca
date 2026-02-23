package com.anthony.devsu.backend.backend_banca.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anthony.devsu.backend.backend_banca.dtos.TransactionsReportDTO;
import com.anthony.devsu.backend.backend_banca.services.TransactionsService;
@RestController
@RequestMapping("/reports")
public class ReportsController {
@Autowired private TransactionsService movimientoService;

    @GetMapping
    public List<TransactionsReportDTO> generarReporte(
            @RequestParam Long customer,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate beginDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) { // Ejemplo: "2023-01-01,2023-12-31"

        LocalDateTime begin = beginDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        return movimientoService.generateReport(customer, begin, end);
    }
}
