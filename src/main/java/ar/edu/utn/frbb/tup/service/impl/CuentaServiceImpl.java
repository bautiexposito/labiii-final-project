package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.CuentaNoSoportadaException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.impl.CuentaDaoImpl;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaDao cuentaDao;
    private final ClienteService clienteService;

    @Autowired
    public CuentaServiceImpl(CuentaDao cuentaDao, ClienteService clienteService) {
        this.cuentaDao = cuentaDao;
        this.clienteService = clienteService;
    }

    @Override
    public void darDeAltaCuenta(Cuenta cuenta) throws CuentaAlreadyExistsException, TipoCuentaAlreadyExistsException, CuentaNoSoportadaException {
        if (!tipoCuentaEstaSoportada(cuenta)) {
            throw new CuentaNoSoportadaException("El tipo de cuenta no está soportado.");
        }

        if (cuentaDao.findCuenta(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta ya existe.");
        }

        cuentaDao.saveCuenta(cuenta);
    }

    @Override
    public boolean tipoCuentaEstaSoportada(Cuenta cuenta) {
        return cuenta.getTipoCuenta() == TipoCuenta.CUENTA_CORRIENTE || cuenta.getTipoCuenta() == TipoCuenta.CAJA_AHORRO;
    }

    @Override
    public Cuenta find(long id) {
        return cuentaDao.findCuenta(id);
    }

    public List<Cuenta> findAll() {
        return cuentaDao.findAll();
    }
}