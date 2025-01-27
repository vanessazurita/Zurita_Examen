package com.espe.usuarios.controllers;

import com.espe.usuarios.models.Servicio;
import com.espe.usuarios.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    public List<Servicio> listar() {
        return servicioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtener(@PathVariable Long id) {
        Optional<Servicio> servicio = servicioService.findById(id);
        if (servicio.isPresent()) {
            return ResponseEntity.ok(servicio.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioService.crearServicio(servicio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizar(@PathVariable Long id, @RequestBody Servicio servicio) {
        Optional<Servicio> servicioOptional = servicioService.findById(id);
        if (servicioOptional.isPresent()) {
            Servicio servicioExistente = servicioOptional.get();
            servicioExistente.setNombre(servicio.getNombre());
            servicioExistente.setDescripcion(servicio.getDescripcion());
            servicioExistente.setPrecio(servicio.getPrecio());
            servicioExistente.setDuracion(servicio.getDuracion());
            return ResponseEntity.ok(servicioService.save(servicioExistente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
