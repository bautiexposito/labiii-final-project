package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cuenta_origen", nullable = false)
    private long cuentaOrigen;
    @Column(name = "cuenta_destino", nullable = false)
    private long cuentaDestino;
    private double monto;
    @Enumerated(EnumType.STRING)
    private TipoMoneda moneda;
    private LocalDateTime fecha;

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
