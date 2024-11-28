package ar.edu.utn.frbb.tup.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TransferenciaDto {

    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private String moneda;
}
