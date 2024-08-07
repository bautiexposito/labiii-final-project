package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.CuentaService;

import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

    @BeforeAll
    public static void setUp() {
        MockitoAnnotations.openMocks(CuentaControllerTest.class);
    }

    @Test
    public void testFindAll() {
        List<Cuenta> cuentasEsperadas = Arrays.asList(new Cuenta(), new Cuenta());

        when(cuentaService.findAll()).thenReturn(cuentasEsperadas);

        ResponseEntity<List<Cuenta>> respuesta = cuentaController.findAll();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    public void testGetCuentaByNumeroDeCuenta(){
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);

        when(cuentaService.findByID(cuenta.getNumeroCuenta())).thenReturn(cuenta);

        ResponseEntity<Cuenta> respuesta = cuentaController.getCuentaByNumeroDeCuenta(cuenta.getNumeroCuenta());

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    public void testAltaCuenta() throws CuentaNoSoportadaException, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException, CantidadNegativaException, DatoIngresadoInvalidoException {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);

        when(cuentaService.darDeAltaCuenta(cuentaDto)).thenReturn(cuenta);

        ResponseEntity<?> respuesta = cuentaController.altaCuenta(cuentaDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(cuenta, respuesta.getBody());
    }

    public CuentaDto getCuentaDto(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setBalance(1000);
        cuentaDto.setTipoCuenta("C");
        cuentaDto.setMoneda("Pesos");
        cuentaDto.setDniTitular(43046272);
        return cuentaDto;
    }
}