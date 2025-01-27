package com.espe.cursos.models;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ServicioDTO {

    @NotNull(message = "El ID del servicio es obligatorio")
    private Long id;

    @NotNull(message = "El nombre del servicio es obligatorio")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @NotNull(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private Double precio;

    @NotNull(message = "La duración es obligatoria")
    private String duracion;

    private LocalDateTime creadoEn;

    public @NotNull(message = "El ID del servicio es obligatorio") Long getId() {
        return id;
    }

    public void setId(@NotNull(message = "El ID del servicio es obligatorio") Long id) {
        this.id = id;
    }

    public @NotNull(message = "El nombre del servicio es obligatorio") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre solo puede contener letras y espacios") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull(message = "El nombre del servicio es obligatorio") @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre solo puede contener letras y espacios") String nombre) {
        this.nombre = nombre;
    }

    public @NotNull(message = "La descripción es obligatoria") @Size(max = 500, message = "La descripción no puede superar los 500 caracteres") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotNull(message = "La descripción es obligatoria") @Size(max = 500, message = "La descripción no puede superar los 500 caracteres") String descripcion) {
        this.descripcion = descripcion;
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
}