package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Random;

@Data
public class Cuenta {

    private final long numeroCuenta;
    private final LocalDateTime fechaCreacion;
    private double balance;
    private TipoCuenta tipoCuenta;
    @JsonBackReference
    Cliente titular;
    TipoMoneda moneda;

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
        Random random = new Random();
        int lowerBound = 1000000;
        int upperBound = 9000000;
        this.numeroCuenta = lowerBound + random.nextInt(upperBound); //genera numero de 7 digitos
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
