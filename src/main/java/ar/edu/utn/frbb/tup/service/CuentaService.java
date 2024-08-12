package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;

import java.util.List;

public interface CuentaService {

    Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, CuentaNoSoportadaException, ClienteNoEncontradoException;

    boolean tipoCuentaEstaSoportada(Cuenta cuenta);

    Cuenta findByID(long id);

    List<Cuenta> findAll();
}