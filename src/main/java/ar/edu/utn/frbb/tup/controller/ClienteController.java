package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.controller.handler.CustomApiError;
import ar.edu.utn.frbb.tup.controller.validator.ClienteValidator;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteValidator clienteValidator;

    @GetMapping
        public ResponseEntity<List<Cliente>> getClientes() {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{dni}")
    public ResponseEntity<Cliente> getClienteByID(@PathVariable("dni") long dni) {
        Cliente cliente = clienteService.buscarClientePorDni(dni);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/alta")
    public ResponseEntity<?> altaCliente(@RequestBody ClienteDto clienteDto) throws ClienteAlreadyExistsException, DatoIngresadoInvalidoException {
        try {
            clienteValidator.validate(clienteDto);
            Cliente cliente = clienteService.darDeAltaCliente(clienteDto);
            return new ResponseEntity<>(cliente, HttpStatus.CREATED);
        } catch(DatoIngresadoInvalidoException e){
            CustomApiError error = new CustomApiError();
            error.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        } catch (ClienteAlreadyExistsException e) {
            CustomApiError error = new CustomApiError();
            error.setErrorMessage(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }
    }
}