package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.controller.dto.ClienteDto;
import ar.edu.utn.frbb.tup.model.Cliente;
import ar.edu.utn.frbb.tup.persistence.impl.ClienteDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteDaoTest {

    private ClienteDao clienteDao;

    @BeforeAll
    public void setUp() {
        clienteDao = new ClienteDaoImpl();
    }

    @Test
    public void testSaveCliente() {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);

        Cliente clienteGuardado = clienteDao.saveCliente(cliente);

        assertNotNull(clienteGuardado);
        assertEquals(12345678L, clienteGuardado.getDni());
        assertEquals("Juan", clienteGuardado.getNombre());
        assertEquals("Perez", clienteGuardado.getApellido());
    }

    @Test
    public void testFindCliente() {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente = new Cliente(clienteDto);

        clienteDao.saveCliente(cliente);
        Cliente clienteObtenido = clienteDao.findCliente(12345678L);

        assertNotNull(clienteObtenido);
        assertEquals(12345678L, clienteObtenido.getDni());
        assertEquals("Juan", clienteObtenido.getNombre());
        assertEquals("Perez", clienteObtenido.getApellido());
    }

    @Test
    public void testFindAll() {
        ClienteDto clienteDto = getClienteDto();
        Cliente cliente1 = new Cliente(clienteDto);
        Cliente cliente2 = new Cliente(clienteDto);
        cliente2.setDni(43046811);

        clienteDao.saveCliente(cliente1);
        clienteDao.saveCliente(cliente2);

        List<Cliente> clientes = clienteDao.findAll();

        assertEquals(2, clientes.size());
        assertTrue(clientes.contains(cliente1));
        assertTrue(clientes.contains(cliente2));
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