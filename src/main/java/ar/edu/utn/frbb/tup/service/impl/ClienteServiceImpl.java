package ar.edu.utn.frbb.tup.service.impl;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoPersona;
import ar.edu.utn.frbb.tup.model.exception.ClienteAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.ClienteMenorDeEdadException;
import ar.edu.utn.frbb.tup.model.exception.ClienteNoEncontradoException;
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

        Cliente clienteExistente = clienteDao.findByDni(cliente.getDni());

        if (clienteExistente != null) {
            throw new ClienteAlreadyExistsException("Ya existe un cliente con DNI " + cliente.getDni());
        }

        if (cliente.getEdad() < 18) {
            throw new ClienteMenorDeEdadException("El cliente debe ser mayor a 18 años");
        }

        clienteDao.save(cliente);
        return cliente;
    }

    @Override
    public void agregarCuenta(Cuenta cuenta, long dniTitular) throws ClienteNoEncontradoException {
        Cliente cliente = buscarClientePorDni(dniTitular);
        if (cliente != null) {
            cliente.getCuentas().add(cuenta);
            cuenta.setTitular(cliente);
            clienteDao.save(cliente);
        }else {
            throw new ClienteNoEncontradoException("Cliente no encontrado con DNI: " + dniTitular);
        }
    }

    @Override
    public Cliente buscarClientePorDni(long dni) throws ClienteNoEncontradoException {
        Cliente cliente = clienteDao.findByDni(dni);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente no existe");
        }
        return cliente;
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDao.findAll();
    }

    @Override
    public Cliente actualizarCliente(ClienteDto clienteDto) throws ClienteNoEncontradoException{
        Cliente clienteExistente = clienteDao.findByDni(clienteDto.getDni());
        if (clienteExistente == null) {
            throw new ClienteNoEncontradoException("El cliente no existe");
        }
        clienteExistente.setNombre(clienteDto.getNombre());
        clienteExistente.setApellido(clienteDto.getApellido());
        clienteExistente.setTipoPersona(TipoPersona.fromString(clienteDto.getTipoPersona()));
        clienteExistente.setFechaNacimiento(clienteDto.getFechaNacimiento());
        return clienteDao.save(clienteExistente);
    }

    @Override
    public List<Cliente> searchCliente(String nombre, String apellido) throws ClienteNoEncontradoException {
        return clienteDao.findByNombreOrApellido(nombre, apellido);
    }

    @Override
    public void eliminarCliente(long dni) throws ClienteNoEncontradoException {
        Cliente cliente = clienteDao.findByDni(dni);
        if (cliente == null) {
            throw new ClienteNoEncontradoException("El cliente no existe");
        }
        clienteDao.deleteByDni(dni);
    }
}