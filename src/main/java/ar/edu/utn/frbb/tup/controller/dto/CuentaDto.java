package ar.edu.utn.frbb.tup.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CuentaDto {

    private String tipoCuenta;
    private String moneda;
    private long dniTitular;
    private double balance;
    private long numeroCuenta;
}
