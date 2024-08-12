package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.ClienteDao;
import ar.edu.utn.frbb.tup.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    ClienteDao clienteDao;

    public ClienteServiceImpl(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    @Override
    public Cliente darDeAltaCliente(ClienteDto clienteDto) throws ClienteAlreadyExistsException, ClienteMenorDeEdadException {
        Cliente cliente = new Cliente(clienteDto);

        Cliente clienteExistente = clienteDao.findCliente(cliente.getDni());

        if (clienteExistente != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + cliente.getDni());
        }

        if (cliente.getEdad() < 18) {
            throw new ClienteMenorDeEdadException("El cliente debe ser mayor a 18 años");
        }

        clienteDao.saveCliente(cliente);
        return cliente;
    }

    @Override
    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws ClienteNoEncontradoException {
        Cliente cliente = buscarClientePorDni(dniTitular);
        if (cliente != null) {
            cliente.getCuentas().add(cuenta);
            cuenta.setTitular(cliente);
            clienteDao.saveCliente(cliente);
        }else {
            throw new ClienteNoEncontradoException("Cliente no encontrado con DNI: " + dniTitular);
        }
    }

    @Override
    public Cliente buscarClientePorDni(long dni) throws ClienteNoEncontradoException {
        Cliente cliente = clienteDao.findCliente(dni);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente no existe");
        }
        return cliente;
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDao.findAll();
    }
}