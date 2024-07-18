package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.model.Banco;
import ar.edu.utn.frbb.tup.model.TipoMoneda;
import ar.edu.utn.frbb.tup.model.exception.CantidadNegativaException;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.NoAlcanzaException;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.service.Banelco;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferenciaServiceImpl implements TransferenciaService {

    private TransferenciaDao transferenciaDao;
    private CuentaDao cuentaDao;
    private TipoMoneda tipoMoneda;
    @Autowired
    private Banco banco;
    private Banelco banelco;


    public TransferenciaServiceImpl(TransferenciaDao transferenciaDao, CuentaDao cuentaDao) {
        this.transferenciaDao = transferenciaDao;
        this.cuentaDao = cuentaDao;
    }

    public List<Transferencia> find(long id){return transferenciaDao.findTransfersByID(id);}

    public List<Transferencia> findAll(){return transferenciaDao.findAllTransfers();}

    @Override
    public void realizarTransferencia(Transferencia transferencia) throws Exception {
        long cuentaOrigenNumero = transferencia.getCuentaOrigen();
        long cuentaDestinoNumero = transferencia.getCuentaDestino();
        double monto = transferencia.getMonto();
        String moneda = transferencia.getMoneda();

        Cuenta cuentaOrigen = cuentaDao.findCuenta(cuentaOrigenNumero);
        Cuenta cuentaDestino = cuentaDao.findCuenta(cuentaDestinoNumero);

        if (cuentaOrigen == null) {throw new Exception("Cuenta origen no encontrada");}

        if (cuentaOrigen.getBalance() < monto) {throw new Exception("Saldo insuficiente en la cuenta origen");}

        if (cuentaDestino == null) {
            if(banelco.cuentaExiste(cuentaDestinoNumero)){
                //implementar
                return;
            } else{
                throw new Exception("Cuenta destino no encontrada");
            }
        }

        if (cuentaOrigen.getMoneda() != cuentaDestino.getMoneda()){throw new Exception("El tipo de moneda de las cuentas bancarias no coincide");}

        if(cuentaOrigen.getMoneda() != tipoMoneda.fromString(moneda)){throw new Exception("El tipo de moneda ingresado, no coincide con el tipo de moneda de las cuentas bancarias");}

        if (cuentaOrigen.getMoneda()==TipoMoneda.PESOS){
            if(monto<1000000){
                cuentaOrigen.debitar(monto);
                cuentaDestino.acreditar(monto);
            } else{
                banco.guardarComisionEnPesos(monto*0.02);
                cuentaOrigen.debitar(monto*0.98);
                cuentaDestino.acreditar(monto*0.98);
            }
        } else if (cuentaOrigen.getMoneda()==TipoMoneda.DOLARES){
            if(monto<5000){
                banco.guardarComisionEnDolares(monto*0.005);
                cuentaOrigen.debitar(monto*0.995);
                cuentaDestino.acreditar(monto*0.995);
            }
        }

        transferencia.setFecha(LocalDate.now());
        transferencia.setEstado("COMPLETADA");

        transferenciaDao.guardarTransferencia(transferencia);

        cuentaDao.actualizarCuenta(cuentaOrigen);
        cuentaDao.actualizarCuenta(cuentaDestino);
    }
}