package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoEncontradaException;

import java.util.List;

public interface CuentaDao {

    Cuenta saveCuenta(Cuenta cuenta);

    Cuenta findCuenta(long numeroCuenta);

    List<Cuenta> findAll();

    void actualizarCuenta(Cuenta cuenta) throws CuentaNoEncontradaException;
}