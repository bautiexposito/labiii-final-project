package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.service.CuentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    CuentaDao cuentaDao;
    ClienteService clienteService;

    @Autowired
    public CuentaServiceImpl(CuentaDao cuentaDao, ClienteService clienteService) {
        this.cuentaDao = cuentaDao;
        this.clienteService = clienteService;
    }

    @Override
    @Transactional
    public Cuenta darDeAltaCuenta(CuentaDto cuentaDto) throws CuentaAlreadyExistsException, CuentaNoSoportadaException, ClienteNoEncontradoException {
        Cuenta cuenta = new Cuenta(cuentaDto);

        if (!tipoCuentaEstaSoportada(cuenta)) {
            throw new CuentaNoSoportadaException("El tipo de cuenta no está soportado.");
        }

        if (cuentaDao.findByNumeroCuenta(cuenta.getNumeroCuenta()) != null) {
            throw new CuentaAlreadyExistsException("La cuenta ya existe.");
        }

        clienteService.agregarCuenta(cuenta, cuentaDto.getDniTitular());
//        cuentaDao.save(cuenta);

        return cuenta;
    }

    @Override
    public boolean tipoCuentaEstaSoportada(Cuenta cuenta) {
        return cuenta.getTipoCuenta() == TipoCuenta.CUENTA_CORRIENTE || cuenta.getTipoCuenta() == TipoCuenta.CAJA_AHORRO;
    }

    @Override
    public Cuenta buscarCuentaPorNumero(long numeroCuenta) throws CuentaNoEncontradaException{
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null){
            throw new CuentaNoEncontradaException("La cuenta no existe");
        }
        return cuenta;
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaDao.findAll();
    }

    @Override
    public void eliminarCuenta(long numeroCuenta) throws CuentaNoEncontradaException {
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null){
            throw new CuentaNoEncontradaException("La cuenta no existe");
        }
        cuentaDao.deleteByNumeroCuenta(numeroCuenta);
    }

    @Override
    public void depositarDinero(long numeroCuenta, double monto) throws CuentaNoEncontradaException {
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null){
            throw new CuentaNoEncontradaException("La cuenta no existe");
        }
        cuenta.acreditar(monto);
        cuentaDao.save(cuenta);
    }

    @Override
    public void extraerDinero(long numeroCuenta, double monto) throws CuentaNoEncontradaException, NoAlcanzaException {
        Cuenta cuenta = cuentaDao.findByNumeroCuenta(numeroCuenta);
        if (cuenta == null){
            throw new CuentaNoEncontradaException("La cuenta no existe");
        }
        cuenta.debitar(monto);
        cuentaDao.save(cuenta);
    }
}
