package com.jpbb.prestamos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String responsable;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "herramienta_id", nullable = false)
    private Herramienta herramienta;

    public Prestamo() {
    }

    public Prestamo(String responsable, LocalDate fechaPrestamo, Integer cantidad, Herramienta herramienta) {
        this.responsable = responsable;
        this.fechaPrestamo = fechaPrestamo;
        this.cantidad = cantidad;
        this.herramienta = herramienta;
    }

    public Prestamo(Long id, String responsable, LocalDate fechaPrestamo, Integer cantidad, Herramienta herramienta) {
        this.id = id;
        this.responsable = responsable;
        this.fechaPrestamo = fechaPrestamo;
        this.cantidad = cantidad;
        this.herramienta = herramienta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Herramienta getHerramienta() {
        return herramienta;
    }

    public void setHerramienta(Herramienta herramienta) {
        this.herramienta = herramienta;
    }
}