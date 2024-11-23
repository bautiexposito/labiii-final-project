package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.*;

import java.util.List;

public interface CuentaService {

    Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, CuentaNoSoportadaException, ClienteNoEncontradoException;

    boolean tipoCuentaEstaSoportada(Cuenta cuenta);

    Cuenta buscarCuentaPorNumero(long id) throws CuentaNoEncontradaException;

    List<Cuenta> findAll();

    void eliminarCuenta(long id) throws CuentaNoEncontradaException;
}