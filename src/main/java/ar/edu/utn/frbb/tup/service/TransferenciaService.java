package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;

import java.util.List;

public interface TransferenciaService {

    Transferencia realizarTransferencia(TransferenciaDto transferenciaDto) throws Exception, NoAlcanzaException, CantidadNegativaException;
    List<Transferencia> find(long id);
    List<Transferencia> findAll();
}