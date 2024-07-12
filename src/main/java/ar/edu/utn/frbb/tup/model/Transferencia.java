package ar.edu.utn.frbb.tup.model;

public class Transferencia {

    private long cuentaOrigen;
    private long cuentaDestino;
    private double monto;
    private String moneda;

    // Constructor
    public Transferencia(long cuentaOrigen, long cuentaDestino, double monto, String moneda) {
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.moneda = moneda;
    }

    public Transferencia() {}

    public Transferencia(String cuentaOrigen, String cuentaDestino, double monto, TipoMoneda moneda) {}

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

    public boolean validarTransferencia() {
        // Verificar que la cuenta de origen y la cuenta de destino sean diferentes
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