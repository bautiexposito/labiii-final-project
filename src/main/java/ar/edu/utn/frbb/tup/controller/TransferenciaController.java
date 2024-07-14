package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import ar.edu.utn.frbb.tup.service.impl.CuentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @GetMapping
    public ResponseEntity<List<Transferencia>> getAll() {
        List<Transferencia> transferencias = transferenciaService.findAll();
        return new ResponseEntity<>(transferencias, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Transferencia>> getByID(@PathVariable("id") long id) {
        List<Transferencia> transferencias = transferenciaService.find(id);
        return new ResponseEntity<>(transferencias, HttpStatus.OK);
    }

    @PostMapping("alta")
    public ResponseEntity<Map<String, String>> postTransfer(@RequestBody Transferencia transferencia) throws NoAlcanzaException, CantidadNegativaException {
        try {
            transferenciaService.realizarTransferencia(transferencia);
            Map<String, String> response = new HashMap<>();
            response.put("estado", "EXITOSO");
            response.put("mensaje", "TRANSFERENCIA EXITOSA");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("estado", "ERROR");
            response.put("mensaje", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}