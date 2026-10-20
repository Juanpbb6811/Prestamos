package com.jpbb.prestamos.service;

import com.jpbb.prestamos.model.Herramienta;
import com.jpbb.prestamos.repository.HerramientaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class HerramientaService {

    private final HerramientaRepository herramientaRepository;

    public HerramientaService(HerramientaRepository herramientaRepository) {
        this.herramientaRepository = herramientaRepository;
    }

    public Herramienta registrarHerramienta(Herramienta herramienta) {
        // Validar que la cantidad no sea negativa
        if (herramienta.getCantidad() == null || herramienta.getCantidad() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se permiten cantidades negativas"
            );
        }

        // 1. Buscar si ya existe la herramienta por nombre y categoría
        Optional<Herramienta> existente = herramientaRepository.findByNombreIgnoreCaseAndCategoriaIgnoreCase(
                herramienta.getNombre().trim(),
                herramienta.getCategoria().trim()
        );

        if (existente.isPresent()) {
            // 2. Si existe, sumar la cantidad ingresada al stock actual
            Herramienta herramientaActual = existente.get();
            int nuevaCantidad = herramientaActual.getCantidad() + herramienta.getCantidad();
            herramientaActual.setCantidad(nuevaCantidad);

            return herramientaRepository.save(herramientaActual);
        } else {
            // 3. Si no existe, crear un registro completamente nuevo
            herramienta.setId(null);
            return herramientaRepository.save(herramienta);
        }
    }

    public List<Herramienta> listarHerramientas() {
        return herramientaRepository.findAll();
    }
}