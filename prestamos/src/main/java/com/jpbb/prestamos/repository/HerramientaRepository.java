package com.jpbb.prestamos.repository;

import com.jpbb.prestamos.model.Herramienta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HerramientaRepository extends JpaRepository<Herramienta, Long> {

    // Busca coincidencias exactas de nombre y categoría ignorando mayúsculas/minúsculas
    Optional<Herramienta> findByNombreIgnoreCaseAndCategoriaIgnoreCase(String nombre, String categoria);
}