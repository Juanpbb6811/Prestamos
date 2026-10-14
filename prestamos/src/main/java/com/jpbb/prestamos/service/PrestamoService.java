package com.jpbb.prestamos.service;

import com.jpbb.prestamos.dto.PrestamoRequest;
import com.jpbb.prestamos.model.Herramienta;
import com.jpbb.prestamos.model.Prestamo;
import com.jpbb.prestamos.repository.HerramientaRepository;
import com.jpbb.prestamos.repository.PrestamoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final HerramientaRepository herramientaRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, HerramientaRepository herramientaRepository) {
        this.prestamoRepository = prestamoRepository;
        this.herramientaRepository = herramientaRepository;
    }

    @Transactional
    public Prestamo registrarPrestamo(PrestamoRequest request) {
        // Regla 1: Rechazar préstamos cuya cantidad sea cero o negativa
        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad a prestar debe ser mayor a cero"
            );
        }

        // Regla 2: Comprobar que la herramienta exista
        Herramienta herramienta = herramientaRepository.findById(request.getHerramientaId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "La herramienta con ID " + request.getHerramientaId() + " no existe"
                ));

        // Regla 3: Rechazar si la cantidad supera las unidades disponibles
        if (request.getCantidad() > herramienta.getCantidad()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unidades insuficientes. Disponibles: " + herramienta.getCantidad() + ", Solicitadas: " + request.getCantidad()
            );
        }

        // Regla 4: Descontar las unidades prestadas del inventario
        herramienta.setCantidad(herramienta.getCantidad() - request.getCantidad());
        herramientaRepository.save(herramienta);

        // Regla 5: Registrar el préstamo en la misma transacción
        Prestamo prestamo = new Prestamo();
        prestamo.setResponsable(request.getResponsable());
        prestamo.setFechaPrestamo(
                request.getFechaPrestamo() != null ? request.getFechaPrestamo() : LocalDate.now()
        );
        prestamo.setCantidad(request.getCantidad());
        prestamo.setHerramienta(herramienta);

        return prestamoRepository.save(prestamo);
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    public List<Prestamo> listarPrestamosPorHerramienta(Long herramientaId) {
        if (!herramientaRepository.existsById(herramientaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "La herramienta con ID " + herramientaId + " no existe"
            );
        }
        return prestamoRepository.findByHerramientaId(herramientaId);
    }
}