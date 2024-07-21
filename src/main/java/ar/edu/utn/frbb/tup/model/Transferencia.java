package ar.edu.utn.frbb.tup.model;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;

import java.time.LocalDate;

public class Transferencia {

    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private String moneda;
    private String estado;
    private String mensaje;
    private LocalDate fecha;
    private String tipo;
    private String descripcionBreve;

    public Transferencia() {
        this.fecha = LocalDate.now();
    }

    public Transferencia(TransferenciaDto transferenciaDto) {
        this.cuentaOrigen = transferenciaDto.getCuentaOrigen();
        this.cuentaDestino = transferenciaDto.getCuentaDestino();
        this.monto = transferenciaDto.getMonto();
        this.moneda = transferenciaDto.getMoneda();
        this.fecha = LocalDate.now();
    }

    public long getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(long cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public long getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(long cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {this.monto = monto;}

    public String getMoneda() {return moneda;}

    public void setMoneda(String moneda) {this.moneda = moneda;}

    public String getEstado() {return estado;}

    public void setEstado(String estado) {this.estado = estado;}

    public String getMensaje() {return mensaje;}

    public void setMensaje(String mensaje) {this.mensaje = mensaje;}

    public LocalDate getFecha() {return fecha;}

    public void setFecha(LocalDate fecha) {this.fecha = fecha;}

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getDescripcionBreve() {return descripcionBreve;}

    public void setDescripcionBreve(String descripcionBreve) {this.descripcionBreve = descripcionBreve;}

    public boolean validarTransferencia() {
        if (cuentaOrigen==cuentaDestino) {
            System.out.println("Error: La cuenta de origen y la cuenta de destino son iguales.");
            return false;
        }

        if (monto <= 0) {
            System.out.println("Error: El monto a transferir debe ser mayor que cero.");
            return false;
        }

        return true;
    }
}