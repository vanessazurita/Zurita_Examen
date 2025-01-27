package com.espe.cursos.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "reserva_servicio")
@Data
public class ReservaServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El ID del servicio es obligatorio")
    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    @NotNull(message = "El nombre del servicio es obligatorio")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre solo puede contener letras y espacios")
    @Column(name = "nombre_servicio")
    private String nombreServicio;

    @NotNull(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    @Column(name = "descripcion_servicio")
    private String descripcionServicio;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(name = "precio")
    private Double precio;

    @NotNull(message = "La duración es obligatoria")
    @Column(name = "duracion")
    private String duracion;

    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    @JsonBackReference
    private Reserva reserva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "El ID del servicio es obligatorio") Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(@NotNull(message = "El ID del servicio es obligatorio") Long servicioId) {
        this.servicioId = servicioId;
    }

    public @NotNull(message = "El nombre del servicio es obligatorio") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre solo puede contener letras y espacios") String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(@NotNull(message = "El nombre del servicio es obligatorio") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre solo puede contener letras y espacios") String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public @NotNull(message = "La descripción es obligatoria") @Size(max = 500, message = "La descripción no puede superar los 500 caracteres") String getDescripcionServicio() {
        return descripcionServicio;
    }

    public void setDescripcionServicio(@NotNull(message = "La descripción es obligatoria") @Size(max = 500, message = "La descripción no puede superar los 500 caracteres") String descripcionServicio) {
        this.descripcionServicio = descripcionServicio;
    }

    public @NotNull(message = "El precio es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0") Double getPrecio() {
        return precio;
    }

    public void setPrecio(@NotNull(message = "El precio es obligatorio") @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0") Double precio) {
        this.precio = precio;
    }

    public @NotNull(message = "La duración es obligatoria") String getDuracion() {
        return duracion;
    }

    public void setDuracion(@NotNull(message = "La duración es obligatoria") String duracion) {
        this.duracion = duracion;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}