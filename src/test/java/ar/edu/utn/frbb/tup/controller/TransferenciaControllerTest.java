package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import ar.edu.utn.frbb.tup.controller.validator.TransferenciaValidator;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferenciaControllerTest {

    @Mock
    private TransferenciaService transferenciaService;

    @Mock
    private TransferenciaValidator transferenciaValidator;

    @InjectMocks
    private TransferenciaController transferenciaController;

    @BeforeAll
    public static void setUp() {
        MockitoAnnotations.openMocks(TransferenciaControllerTest.class);
    }

    @Test
    public void testGetAll() {
        List<Transferencia> transferencias = List.of(new Transferencia());
        when(transferenciaService.findAll()).thenReturn(transferencias);

        ResponseEntity<List<Transferencia>> respuesta = transferenciaController.getAll();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(transferencias, respuesta.getBody());
    }

    @Test
    public void testGetByID() {
        TransferenciaDto transferenciaDto = getTransferenciaDto();
        Transferencia transferencia1 = new Transferencia(transferenciaDto);
        Transferencia transferencia2 = new Transferencia(transferenciaDto);
        long id = transferencia1.getCuentaOrigen();

        List<Transferencia> transferencias = List.of(transferencia1,transferencia2);

        when(transferenciaService.find(id)).thenReturn(transferencias);

        ResponseEntity<Map<String, Object>> respuesta = transferenciaController.getByID(id);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(2, transferencias.size());
    }

    @Test
    public void testPostTransfer() throws Exception, NoAlcanzaException, CantidadNegativaException {
        TransferenciaDto transferenciaDto = getTransferenciaDto();
        Transferencia transferencia = new Transferencia(transferenciaDto);

        when(transferenciaService.realizarTransferencia(any())).thenReturn(transferencia);

        ResponseEntity<?> responseEntity = transferenciaController.postTransfer(transferenciaDto);
        Map<String, String> responseBody = (Map<String, String>) responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("EXITOSO", responseBody.get("estado"));
        assertEquals("TRANSFERENCIA EXITOSA", responseBody.get("mensaje"));
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