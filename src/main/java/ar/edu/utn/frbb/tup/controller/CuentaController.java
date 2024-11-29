package ar.edu.utn.frbb.tup.controller;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.controller.dto.TransaccionDto;
import ar.edu.utn.frbb.tup.controller.validator.CuentaValidator;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.*;
import ar.edu.utn.frbb.tup.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaValidator cuentaValidator;

    @GetMapping
    public ResponseEntity<List<Cuenta>> findAll() {
        List<Cuenta> cuentas = cuentaService.findAll();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaByNumeroDeCuenta(@PathVariable("id") long id) throws CuentaNoEncontradaException {
        Cuenta cuenta = cuentaService.buscarCuentaPorNumero(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @PostMapping("/alta")
    public ResponseEntity<?> altaCuenta(@RequestBody CuentaDto cuentaDto) throws CuentaNoSoportadaException, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException, CantidadNegativaException, DatoIngresadoInvalidoException, ClienteNoEncontradoException {
        cuentaValidator.validate(cuentaDto);
        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto);
        return new ResponseEntity<>(cuenta, HttpStatus.CREATED);
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> depositarDinero(@RequestBody TransaccionDto transaccionDto) throws CuentaNoEncontradaException, CantidadNegativaException{
        cuentaValidator.validateMonto(transaccionDto);
        cuentaService.depositarDinero(transaccionDto.getNumeroCuenta(), transaccionDto.getMonto());
        return new ResponseEntity<>("Deposito realizado con exito", HttpStatus.OK);
    }

    @PostMapping("/extraccion")
    public ResponseEntity<?> extraerDinero(@RequestBody TransaccionDto transaccionDto) throws CuentaNoEncontradaException, CantidadNegativaException, NoAlcanzaException {
        cuentaValidator.validateMonto(transaccionDto);
        cuentaService.extraerDinero(transaccionDto.getNumeroCuenta(), transaccionDto.getMonto());
        return new ResponseEntity<>("Extraccion realizada con exito", HttpStatus.OK);
    }

    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<String> eliminarCuenta(@PathVariable("numeroCuenta") long numeroCuenta) throws CuentaNoEncontradaException{
        cuentaService.eliminarCuenta(numeroCuenta);
        return new ResponseEntity<>("Cuenta eliminada correctamente",HttpStatus.OK);
    }
}