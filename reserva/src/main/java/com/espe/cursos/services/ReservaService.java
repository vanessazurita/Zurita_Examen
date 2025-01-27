package com.espe.cursos.services;

import com.espe.cursos.models.Reserva;
import com.espe.cursos.models.ReservaServicio;
import com.espe.cursos.repositories.ReservaRepository;
import com.espe.cursos.repositories.ReservaServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaServicioRepository reservaServicioRepository; // Repositorio para la relación entre reserva y servicio

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }

    public Reserva crearReserva(Reserva reserva) {
        // Validar la lógica necesaria antes de guardar
        return reservaRepository.save(reserva);
    }

    public void cancelarReserva(Long id) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(id);
        if (reservaOptional.isPresent()) {
            reservaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Reserva no encontrada");
        }
    }

    // Asigna un servicio a una reserva
    public Optional<ReservaServicio> asignarServicio(Long reservaId, Long servicioId) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(reservaId);
        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();

            // Crear una nueva instancia de ReservaServicio
            ReservaServicio reservaServicio = new ReservaServicio();
            reservaServicio.setReservaId(reservaId);
            reservaServicio.setServicioId(servicioId);

            // Aquí puedes obtener más información sobre el servicio, como el nombre y la descripción, si lo necesitas

            // Guardar la relación en la base de datos
            reservaServicioRepository.save(reservaServicio);
            return Optional.of(reservaServicio);
        }
        return Optional.empty();  // Retorna vacío si no se encuentra la reserva
    }

    // Elimina la relación entre reserva y servicio
    public void eliminarServicioDeReserva(Long reservaId, Long servicioId) {
        Optional<ReservaServicio> reservaServicioOptional = reservaServicioRepository.findByReservaIdAndServicioId(reservaId, servicioId);
        if (reservaServicioOptional.isPresent()) {
            reservaServicioRepository.delete(reservaServicioOptional.get());
        } else {
            throw new IllegalArgumentException("No se encontró la relación entre la reserva y el servicio");
        }
    }
}
