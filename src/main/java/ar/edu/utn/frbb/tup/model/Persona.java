package ar.edu.utn.frbb.tup.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;

@Data
public class Persona {
    private String nombre;
    private String apellido;
    private long dni;
    private LocalDate fechaNacimiento;

    public Persona(){}

    public Persona(String nombre, String apellido, long dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
    }

    public int getEdad() {
        LocalDate currentDate = LocalDate.now();
        Period agePeriod = Period.between(fechaNacimiento, currentDate);
        return agePeriod.getYears();
    }
}
