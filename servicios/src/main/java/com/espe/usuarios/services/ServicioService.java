package com.espe.usuarios.services;

import com.espe.usuarios.models.Servicio;
import com.espe.usuarios.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> findById(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public void deleteById(Long id) {
        servicioRepository.deleteById(id);
    }

    public Servicio crearServicio(Servicio servicio) {
        // Validar si ya existe un servicio con el mismo nombre
        boolean servicioExistente = servicioRepository.existsByNombre(servicio.getNombre());
        if (servicioExistente) {
            throw new IllegalArgumentException("Ya existe un servicio con el mismo nombre");
        }

        // Guardar el servicio en la base de datos
        return servicioRepository.save(servicio);
    }
}
