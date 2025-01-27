package com.espe.cursos.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "reservas")
@Data
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del cliente es obligatorio")
    @Size(max = 100, message = "El nombre del cliente no puede tener más de 100 caracteres")
    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @NotNull(message = "La fecha y hora de la reserva son obligatorias")
    @Future(message = "La fecha y hora de la reserva deben estar en el futuro")
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    @NotNull(message = "El número de personas es obligatorio")
    @Min(value = 1, message = "El número de personas debe ser al menos 1")
    @Column(name = "numero_personas")
    private Integer numeroPersonas;

    @Size(max = 255, message = "Los comentarios no pueden tener más de 255 caracteres")
    @Column(name = "comentarios")
    private String comentarios;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ReservaServicio> reservaServicios;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "El nombre del cliente es obligatorio") @Size(max = 100, message = "El nombre del cliente no puede tener más de 100 caracteres") String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(@NotNull(message = "El nombre del cliente es obligatorio") @Size(max = 100, message = "El nombre del cliente no puede tener más de 100 caracteres") String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public @NotNull(message = "La fecha y hora de la reserva son obligatorias") @Future(message = "La fecha y hora de la reserva deben estar en el futuro") LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(@NotNull(message = "La fecha y hora de la reserva son obligatorias") @Future(message = "La fecha y hora de la reserva deben estar en el futuro") LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public @NotNull(message = "El número de personas es obligatorio") @Min(value = 1, message = "El número de personas debe ser al menos 1") Integer getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(@NotNull(message = "El número de personas es obligatorio") @Min(value = 1, message = "El número de personas debe ser al menos 1") Integer numeroPersonas) {
        this.numeroPersonas = numeroPersonas;
    }

    public @Size(max = 255, message = "Los comentarios no pueden tener más de 255 caracteres") String getComentarios() {
        return comentarios;
    }

    public void setComentarios(@Size(max = 255, message = "Los comentarios no pueden tener más de 255 caracteres") String comentarios) {
        this.comentarios = comentarios;
    }

    public List<ReservaServicio> getReservaServicios() {
        return reservaServicios;
    }

    public void setReservaServicios(List<ReservaServicio> reservaServicios) {
        this.reservaServicios = reservaServicios;
    }
}