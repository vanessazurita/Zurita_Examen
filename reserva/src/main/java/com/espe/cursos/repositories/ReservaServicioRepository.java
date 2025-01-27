package com.espe.cursos.repositories;

import com.espe.cursos.models.ReservaServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservaServicioRepository extends JpaRepository<ReservaServicio, Long> {

    // Buscar la relación entre una reserva y un servicio específicos
    Optional<ReservaServicio> findByReservaIdAndServicioId(Long reservaId, Long servicioId);
}
