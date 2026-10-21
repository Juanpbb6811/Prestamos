package com.jpbb.prestamos.controller;

import com.jpbb.prestamos.dto.PrestamoRequest;
import com.jpbb.prestamos.model.Prestamo;
import com.jpbb.prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<Prestamo> registrarPrestamo(@Valid @RequestBody PrestamoRequest request) {
        Prestamo nuevo = prestamoService.registrarPrestamo(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> consultarPrestamos() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    @GetMapping("/herramienta/{id}")
    public ResponseEntity<List<Prestamo>> consultarPrestamosPorHerramienta(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.listarPrestamosPorHerramienta(id));
    }
    @PutMapping("/{id}/devolver")
    public ResponseEntity<Prestamo> devolverPrestamo(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverPrestamo(id));
    }
}