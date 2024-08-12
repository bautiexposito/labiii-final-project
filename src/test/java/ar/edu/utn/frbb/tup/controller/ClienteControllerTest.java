package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.*;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.DatoIngresadoInvalidoException;
import ar.edu.utn.frbb.tup.service.ClienteService;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteValidator clienteValidator;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeAll
    public static void setUp() {
        MockitoAnnotations.openMocks(ClienteControllerTest.class);
    }

    @Test
    public void testGetClientes() {
        List<Cliente> clientesEsperados = Arrays.asList(new Cliente(), new Cliente());
        when(clienteService.obtenerTodosLosClientes()).thenReturn(clientesEsperados);

        ResponseEntity<List<Cliente>> respuesta = clienteController.getClientes();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(clientesEsperados, respuesta.getBody());
    }

    @Test
    public void testGetClienteByDNI() throws ClienteNoEncontradoException {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);

        when(clienteService.buscarClientePorDni(cliente.getDni())).thenReturn(cliente);

        ResponseEntity<Cliente> respuesta = clienteController.getClienteByID(cliente.getDni());

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(cliente, respuesta.getBody());
    }

    @Test
    public void testAltaCliente() throws ClienteAlreadyExistsException, DatoIngresadoInvalidoException, ClienteMenorDeEdadException {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);

        when(clienteService.darDeAltaCliente(clienteDto)).thenReturn(cliente);

        ResponseEntity<?> respuesta = clienteController.altaCliente(clienteDto);

        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(cliente, respuesta.getBody());
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
}