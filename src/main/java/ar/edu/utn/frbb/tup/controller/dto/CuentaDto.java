package ar.edu.utn.frbb.tup.controller.dto;

public class CuentaDto {

    private String tipoCuenta;
    private String moneda;
    private long dniTitular;

    public long getDniTitular() {return dniTitular;}

    public String getMoneda() {
        return moneda;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setDniTitular(long dniTitular) {this.dniTitular = dniTitular;}

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}