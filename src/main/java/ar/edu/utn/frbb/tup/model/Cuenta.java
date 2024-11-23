package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@Entity
@Table(name = "cuentas")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private long numeroCuenta;
    private LocalDateTime fechaCreacion;
    private double balance;
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;
    @Enumerated(EnumType.STRING)
    TipoMoneda moneda;
    @ManyToOne
    @JoinColumn(name = "titular_id", nullable = false)
    @JsonBackReference
    Cliente titular;

    public Cuenta() {
        this.numeroCuenta = generarNumeroCuenta();
        this.fechaCreacion = LocalDateTime.now();
        this.balance = 0;
    }

    public Cuenta(CuentaDto cuentaDto){
        this.tipoCuenta = TipoCuenta.fromString(cuentaDto.getTipoCuenta());
        this.moneda = TipoMoneda.fromString(cuentaDto.getMoneda());
        this.fechaCreacion = LocalDateTime.now();
        this.balance = cuentaDto.getBalance();
        this.numeroCuenta = generarNumeroCuenta();
    }

    public void debitar(double monto) throws NoAlcanzaException {
        if (monto > balance) {
            throw new NoAlcanzaException("Saldo insuficiente");
        }
        this.balance -= monto;
    }

    public void acreditar(double monto) {
        this.balance += monto;
    }

    private long generarNumeroCuenta() {
        Random random = new Random();
        int lowerBound = 1000000;
        int upperBound = 9000000;
        return lowerBound + random.nextInt(upperBound);
    }
}
