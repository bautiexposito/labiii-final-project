package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.service.impl.TransferenciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaServiceTest {

    @Mock
    CuentaDao cuentaDao;

    @Mock
    TransferenciaDao transferenciaDao;

    @Mock
    Banelco banelco;

    @InjectMocks
    TransferenciaServiceImpl transferenciaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRealizarTransferencia() throws Exception {
        TransferenciaDto transferenciaDto = getTransferenciaDto();

        CuentaDto cuentaDtoOrigen = getCuentaDtoOrigen();
        Cuenta cuentaOrigen = new Cuenta(cuentaDtoOrigen);

        CuentaDto cuentaDtoDestino = getCuentaDtoDestino();
        Cuenta cuentaDestino = new Cuenta(cuentaDtoDestino);

        when(cuentaDao.findCuenta(1L)).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(2L)).thenReturn(cuentaDestino);

        Transferencia transferencia = transferenciaService.realizarTransferencia(transferenciaDto);

        assertNotNull(transferencia);
    }

    @Test
    public void testRealizarTransferencia_ErrorTipoMoneda() throws Exception {
        TransferenciaDto transferenciaDto = getTransferenciaDto();

        CuentaDto cuentaDtoOrigen = getCuentaDtoOrigen();
        Cuenta cuentaOrigen = new Cuenta(cuentaDtoOrigen);

        when(cuentaDao.findCuenta(1L)).thenReturn(cuentaOrigen);

        assertThrows(Exception.class, () -> transferenciaService.realizarTransferencia(transferenciaDto));
    }

    @Test
    public void testRealizarTransferencia_ErrorCuentaOrigenNoEncontrada() {
        TransferenciaDto transferenciaDto = getTransferenciaDto();

        when(cuentaDao.findCuenta(1L)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            transferenciaService.realizarTransferencia(transferenciaDto);
        });

        assertEquals("Cuenta origen no encontrada", exception.getMessage());
    }

    @Test
    public void testRealizarTransferencia_ErrorCuentaDestinoNoEncontrada() throws Exception {
        TransferenciaDto transferenciaDto = getTransferenciaDto();

        CuentaDto cuentaDtoOrigen = getCuentaDtoOrigen();
        Cuenta cuentaOrigen = new Cuenta(cuentaDtoOrigen);

        when(cuentaDao.findCuenta(1L)).thenReturn(cuentaOrigen);
        when(cuentaDao.findCuenta(2L)).thenReturn(null);
        when(banelco.cuentaExiste(2L)).thenReturn(false);

        assertThrows(Exception.class, () -> transferenciaService.realizarTransferencia(transferenciaDto));

        verify(cuentaDao, never()).updateCuenta(any());
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