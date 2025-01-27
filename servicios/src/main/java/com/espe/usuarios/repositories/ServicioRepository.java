package com.espe.usuarios.repositories;

import com.espe.usuarios.models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    boolean existsByNombre(String nombre);
}
