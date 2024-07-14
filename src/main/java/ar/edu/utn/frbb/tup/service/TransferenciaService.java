package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;

import java.util.List;

public interface TransferenciaService {

    void realizarTransferencia(Transferencia transferencia) throws Exception, NoAlcanzaException, CantidadNegativaException;
    List<Transferencia> find(long id);
    List<Transferencia> findAll();
}