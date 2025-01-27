package com.espe.cursos.controllers;

import com.espe.cursos.models.Reserva;
import com.espe.cursos.models.ReservaServicio;
import com.espe.cursos.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Listar todas las reservas
    @GetMapping
    public List<Reserva> listar() {
        return reservaService.findAll();
    }

    // Obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtener(@PathVariable Long id) {
        return reservaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva reserva
    @PostMapping
    public ResponseEntity<Reserva> crear(@RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.save(reserva));
    }

    // Actualizar una reserva existente
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

    // Asignar un servicio a una reserva
    @PutMapping("/asignar-servicio/{reservaId}")
    public ResponseEntity<?> asignarServicio(@PathVariable Long reservaId, @RequestBody Long servicioId) {
        Optional<ReservaServicio> reservaServicioOptional = reservaService.asignarServicio(reservaId, servicioId);
        if (reservaServicioOptional.isPresent()) {
            return ResponseEntity.ok(reservaServicioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un servicio de una reserva
    @DeleteMapping("/eliminar-servicio/{reservaId}/servicio/{servicioId}")
    public ResponseEntity<Void> eliminarServicioDeReserva(@PathVariable Long reservaId, @PathVariable Long servicioId) {
        reservaService.eliminarServicioDeReserva(reservaId, servicioId);
        return ResponseEntity.noContent().build();
    }

    // Eliminar una reserva por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        reservaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
