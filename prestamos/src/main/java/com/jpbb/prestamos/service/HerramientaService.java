package com.jpbb.prestamos.service;

import com.jpbb.prestamos.model.Herramienta;
import com.jpbb.prestamos.repository.HerramientaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class HerramientaService {

    private final HerramientaRepository herramientaRepository;

    public HerramientaService(HerramientaRepository herramientaRepository) {
        this.herramientaRepository = herramientaRepository;
    }

    public Herramienta registrarHerramienta(Herramienta herramienta) {
        if (herramienta.getCantidad() == null || herramienta.getCantidad() < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se permiten cantidades negativas"
            );
        }
        herramienta.setId(null);
        return herramientaRepository.save(herramienta);
    }

    public List<Herramienta> listarHerramientas() {
        return herramientaRepository.findAll();
    }
}