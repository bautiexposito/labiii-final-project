package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.impl.TransferenciaDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaDaoTest {

    private TransferenciaDao transferenciaDao;

    @BeforeAll
    public void setUp() {
        transferenciaDao = new TransferenciaDaoImpl();
    }

    @Test
    public void testGuardarTransferencia() {
        TransferenciaDto transferenciaDto = getTransferenciaDto();
        Transferencia transferencia = new Transferencia(transferenciaDto);

        transferenciaDao.guardarTransferencia(transferencia);

        List<Transferencia> transferencias = transferenciaDao.findAllTransfers();
        assertEquals(1, transferencias.size());
    }

    @Test
    public void testFindTransfersByID() {
        TransferenciaDto tranferenciaDto = getTransferenciaDto();
        Transferencia transferencia1 = new Transferencia(tranferenciaDto);
        Transferencia transferencia2 = new Transferencia(tranferenciaDto);

        transferenciaDao.guardarTransferencia(transferencia1);
        transferenciaDao.guardarTransferencia(transferencia2);

        List<Transferencia> resultado = transferenciaDao.findTransfersByID(1L);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(transferencia1));
        assertTrue(resultado.contains(transferencia2));
    }

    @Test
    public void testFindAllTransfers() {
        TransferenciaDto tranferenciaDto = getTransferenciaDto();
        Transferencia transferencia1 = new Transferencia(tranferenciaDto);
        Transferencia transferencia2 = new Transferencia(tranferenciaDto);
        transferenciaDao.guardarTransferencia(transferencia1);
        transferenciaDao.guardarTransferencia(transferencia2);

        List<Transferencia> resultado = transferenciaDao.findAllTransfers();

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(transferencia1));
        assertTrue(resultado.contains(transferencia2));
    }

    public CuentaDto getCuentaDtoOrigen(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setBalance(1000);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("Pesos");
        cuentaDto.setDniTitular(43046272);
        return cuentaDto;
    }

    public CuentaDto getCuentaDtoDestino(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setBalance(1000);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("Pesos");
        cuentaDto.setDniTitular(43046272);
        return cuentaDto;
    }

    public ClienteDto getClienteDto(){
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setNombre("Juan");
        clienteDto.setApellido("Perez");
        clienteDto.setDni(12345678L);
        clienteDto.setFechaNacimiento(LocalDate.of(1997, 10, 17));
        clienteDto.setTipoPersona("F");
        return clienteDto;
    }

    public TransferenciaDto getTransferenciaDto(){
        TransferenciaDto transferenciaDto = new TransferenciaDto();
        transferenciaDto.setCuentaOrigen(1L);
        transferenciaDto.setCuentaDestino(2L);
        transferenciaDto.setMonto(99);
        transferenciaDto.setMoneda("P");
        return transferenciaDto;
    }
}