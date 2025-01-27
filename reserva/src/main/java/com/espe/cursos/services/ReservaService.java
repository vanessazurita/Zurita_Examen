package com.espe.cursos.services;

import com.espe.cursos.models.Reserva;
import com.espe.cursos.models.ReservaServicio;
import com.espe.cursos.models.ServicioDTO;
import com.espe.cursos.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String SERVICIO_API_URL = "http://localhost:8001/api/servicios/";

    @Transactional(readOnly = true)
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    @Transactional
    public Reserva save(Reserva reserva) {
        if (reserva.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha de reserva no puede ser en el pasado");
        }
        return reservaRepository.save(reserva);
    }

    @Transactional
    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }

    @Transactional
    public Optional<ReservaServicio> asignarServicioAReserva(Long reservaId, Long servicioId) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(reservaId);
        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();

            // Validar si el servicio ya está asignado a la reserva
            boolean servicioYaAsignado = reserva.getReservaServicios().stream()
                    .anyMatch(rs -> rs.getServicioId().equals(servicioId));
            if (servicioYaAsignado) {
                throw new IllegalArgumentException("Este servicio ya está asignado a la reserva");
            }

            try {
                // Llamada al microservicio de servicios
                ServicioDTO servicio = restTemplate.getForObject(
                        SERVICIO_API_URL + servicioId,
                        ServicioDTO.class
                );

                if (servicio == null) {
                    throw new IllegalArgumentException("No se encuentra el servicio solicitado");
                }

                ReservaServicio reservaServicio = new ReservaServicio();
                reservaServicio.setServicioId(servicio.getId());
                reservaServicio.setNombreServicio(servicio.getNombre());
                reservaServicio.setDescripcionServicio(servicio.getDescripcion());
                reservaServicio.setPrecio(servicio.getPrecio());
                reservaServicio.setDuracion(servicio.getDuracion());
                reservaServicio.setCreadoEn(LocalDateTime.now());
                reservaServicio.setReserva(reserva);

                reserva.getReservaServicios().add(reservaServicio);
                reservaRepository.save(reserva);

                return Optional.of(reservaServicio);
            } catch (Exception e) {
                if (e.getMessage().contains("Connection refused")) {
                    throw new RuntimeException("Error de conexión con el servicio de servicios");
                }
                throw new RuntimeException("Error al asignar servicio: " + e.getMessage());
            }
        }
        return Optional.empty();
    }

    @Transactional
    public boolean eliminarServicioDeReserva(Long reservaId, Long servicioId) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(reservaId);
        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();
            boolean eliminado = reserva.getReservaServicios().removeIf(
                    rs -> rs.getServicioId().equals(servicioId)
            );
            if (eliminado) {
                reservaRepository.save(reserva);
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<ReservaServicio> listarServiciosDeReserva(Long reservaId) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(reservaId);
        if (reservaOptional.isPresent()) {
            return reservaOptional.get().getReservaServicios();
        }
        throw new IllegalArgumentException("La reserva con ID " + reservaId + " no existe.");
    }

    @Transactional(readOnly = true)
    public List<Reserva> listarReservasPorServicio(Long servicioId) {
        return reservaRepository.findAll().stream()
                .filter(reserva -> reserva.getReservaServicios().stream()
                        .anyMatch(servicio -> servicio.getServicioId().equals(servicioId)))
                .collect(Collectors.toList());
    }

    // Métodos adicionales que podrían ser útiles

    @Transactional(readOnly = true)
    public List<Reserva> buscarReservasPorFecha(LocalDateTime fecha) {
        return reservaRepository.findAll().stream()
                .filter(reserva -> reserva.getFechaHora().toLocalDate()
                        .equals(fecha.toLocalDate()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Reserva> buscarReservasPorCliente(String nombreCliente) {
        return reservaRepository.findAll().stream()
                .filter(reserva -> reserva.getNombreCliente()
                        .toLowerCase()
                        .contains(nombreCliente.toLowerCase()))
                .collect(Collectors.toList());
    }
}