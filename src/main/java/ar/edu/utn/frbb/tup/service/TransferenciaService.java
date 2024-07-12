package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;

public interface TransferenciaService {

    void realizarTransferencia(long cuentaOrigenNumero, long cuentaDestinoNumero, double monto) throws Exception, NoAlcanzaException, CantidadNegativaException;
}