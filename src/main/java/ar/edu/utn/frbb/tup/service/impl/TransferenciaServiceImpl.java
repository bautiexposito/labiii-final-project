package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    private TransferenciaDao transferenciaDao;
    private CuentaDao cuentaDao;

    public TransferenciaServiceImpl(TransferenciaDao transferenciaDao, CuentaDao cuentaDao) {
        this.transferenciaDao = transferenciaDao;
        this.cuentaDao = cuentaDao;
    }

    @Override
    public void realizarTransferencia(Transferencia transferencia) throws Exception {
        long cuentaOrigenNumero = transferencia.getCuentaOrigen();
        long cuentaDestinoNumero = transferencia.getCuentaDestino();
        double monto = transferencia.getMonto();
        String moneda = transferencia.getMoneda();

        Cuenta cuentaOrigen = cuentaDao.findCuenta(cuentaOrigenNumero);
        Cuenta cuentaDestino = cuentaDao.findCuenta(cuentaDestinoNumero);

        if (cuentaOrigen == null) {
            throw new Exception("Cuenta origen no encontrada");
        }
        if (cuentaDestino == null) {
            throw new Exception("Cuenta destino no encontrada");
        }

        if (cuentaOrigen.getBalance() < monto) {
            throw new Exception("Saldo insuficiente en la cuenta origen");
        }

        if (!moneda.equals("pesos") && !moneda.equals("dolares")) {
            throw new Exception("Tipo de moneda no válida");
        }

        cuentaOrigen.debitar(monto);
        cuentaDestino.acreditar(monto);

        transferencia.setFecha(LocalDate.now());
        transferencia.setEstado("COMPLETADA");

        transferenciaDao.guardarTransferencia(transferencia);

        cuentaDao.actualizarCuenta(cuentaOrigen);
        cuentaDao.actualizarCuenta(cuentaDestino);
    }

    public List<Transferencia> find(long id){return transferenciaDao.findTransfersByID(id);}
    public List<Transferencia> findAll(){return transferenciaDao.findAllTransfers();}
}