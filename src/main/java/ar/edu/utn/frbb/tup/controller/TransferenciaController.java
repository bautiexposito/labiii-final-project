package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.TransferenciaDto;
import ar.edu.utn.frbb.tup.controller.validator.TransferenciaValidator;
import ar.edu.utn.frbb.tup.model.Transferencia;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;
    @Autowired
    private TransferenciaValidator transferenciaValidator;

    @GetMapping("/transacciones")
    public ResponseEntity<List<Transferencia>> getAll() {
        List<Transferencia> transferencias = transferenciaService.findAll();
        return new ResponseEntity<>(transferencias, HttpStatus.OK);
    }

    @GetMapping("/cuenta/{cuentaId}/transacciones")
    public ResponseEntity<Map<String, Object>> getByID(@PathVariable("cuentaId") long cuentaId) {
        List<Transferencia> transferencias = transferenciaService.find(cuentaId);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("numeroCuenta", cuentaId);
        response.put("transacciones", transferencias.stream().map(t -> {
            Map<String, Object> transaccion = new LinkedHashMap<>();
            transaccion.put("fecha", t.getFecha().format(DateTimeFormatter.ISO_DATE));
            if(cuentaId == t.getCuentaOrigen()){
                transaccion.put("tipo", "DEBITO");
                transaccion.put("descripcionBreve", "Transferencia saliente");
            }else if(cuentaId == t.getCuentaDestino()){
                transaccion.put("tipo", "CREDITO");
                transaccion.put("descripcionBreve", "Transferencia entrante");
            }
            transaccion.put("monto", t.getMonto());
            return transaccion;
        }).collect(Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, String>> postTransfer(@RequestBody TransferenciaDto transferenciaDto) throws NoAlcanzaException, CantidadNegativaException {
        transferenciaValidator.validate(transferenciaDto);
        try {
            transferenciaService.realizarTransferencia(transferenciaDto);
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