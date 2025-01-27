package com.espe.cursos.controllers;

import com.espe.cursos.models.Reserva;
import com.espe.cursos.models.ReservaServicio;
import com.espe.cursos.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public List<Reserva> listar() {
        return reservaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtener(@PathVariable Long id) {
        return reservaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{reservaId}/servicios")
    public ResponseEntity<List<ReservaServicio>> listarServiciosDeReserva(@PathVariable Long reservaId) {
        try {
            List<ReservaServicio> servicios = reservaService.listarServiciosDeReserva(reservaId);
            return ResponseEntity.ok(servicios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/servicio/{servicioId}/reservas")
    public ResponseEntity<List<Reserva>> listarReservasPorServicio(@PathVariable Long servicioId) {
        List<Reserva> reservas = reservaService.listarReservasPorServicio(servicioId);
        if (!reservas.isEmpty()) {
            return ResponseEntity.ok(reservas);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva reserva) {
        try {
            return ResponseEntity.ok(reservaService.save(reserva));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> actualizarReserva(@PathVariable Long id, @RequestBody Reserva reservaActualizada) {
        Optional<Reserva> reservaOptional = reservaService.findById(id);
        if (reservaOptional.isPresent()) {
            Reserva reservaExistente = reservaOptional.get();
            reservaExistente.setNombreCliente(reservaActualizada.getNombreCliente());
            reservaExistente.setFechaHora(reservaActualizada.getFechaHora());
            reservaExistente.setNumeroPersonas(reservaActualizada.getNumeroPersonas());
            reservaExistente.setComentarios(reservaActualizada.getComentarios());
            Reserva reservaGuardada = reservaService.save(reservaExistente);
            return ResponseEntity.ok(reservaGuardada);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-servicio/{reservaId}")
    public ResponseEntity<ReservaServicio> asignarServicio(@PathVariable Long reservaId, @RequestBody Long servicioId) {
        Optional<ReservaServicio> reservaServicioOptional = reservaService.asignarServicioAReserva(reservaId, servicioId);
        if (reservaServicioOptional.isPresent()) {
            ReservaServicio reservaServicio = reservaServicioOptional.get();
            // Limpia la referencia a la reserva para evitar redundancia
            reservaServicio.setReserva(null);
            return ResponseEntity.ok(reservaServicio);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-servicio/{reservaId}/servicio/{servicioId}")
    public ResponseEntity<Void> eliminarServicioDeReserva(@PathVariable Long reservaId, @PathVariable Long servicioId) {
        if (reservaService.eliminarServicioDeReserva(reservaId, servicioId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}