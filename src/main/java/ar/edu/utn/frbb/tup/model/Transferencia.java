package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transferencia {

    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private TipoMoneda moneda;
    private final LocalDateTime fecha;

    public Transferencia() {
        this.fecha = LocalDateTime.now();
    }

    public Transferencia(TransferenciaDto transferenciaDto) {
        this.cuentaOrigen = transferenciaDto.getCuentaOrigen();
        this.cuentaDestino = transferenciaDto.getCuentaDestino();
        this.monto = transferenciaDto.getMonto();
        this.moneda = TipoMoneda.fromString(transferenciaDto.getMoneda());
        this.fecha = LocalDateTime.now();
    }
}
