package ar.edu.utn.frbb.tup.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class PersonaDto {
    private String nombre;
    private String apellido;
    private long dni;
    private LocalDate fechaNacimiento;
}
