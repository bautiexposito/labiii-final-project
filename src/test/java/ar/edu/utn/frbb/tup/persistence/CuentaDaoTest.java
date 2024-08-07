package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.persistence.impl.CuentaDaoImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaDaoTest {

    private CuentaDao cuentaDao;

    @BeforeAll
    public void setUp() {
        cuentaDao = new CuentaDaoImpl();
    }

    @Test
    public void testSaveCuenta() {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);
        long id = cuenta.getNumeroCuenta();

        Cuenta cuentaGuardada = cuentaDao.saveCuenta(cuenta);

        assertNotNull(cuentaGuardada);
        assertEquals(id, cuentaGuardada.getNumeroCuenta());
        assertEquals(1000, cuentaGuardada.getBalance());
    }

    @Test
    public void testFindCuenta(){
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);
        long id = cuenta.getNumeroCuenta();

        cuentaDao.saveCuenta(cuenta);
        Cuenta cuentaObtenida = cuentaDao.findCuenta(id);

        assertNotNull(cuentaObtenida);
        assertEquals(id, cuentaObtenida.getNumeroCuenta());
        assertEquals(1000, cuentaObtenida.getBalance());
    }

    @Test
    public void testFindAll() {
        CuentaDto cuentaDto1 = getCuentaDto();
        Cuenta cuenta1 = new Cuenta(cuentaDto1);
        CuentaDto cuentaDto2 = getCuentaDto();
        cuentaDto2.setDniTitular(43072111);
        Cuenta cuenta2 = new Cuenta(cuentaDto2);

        cuentaDao.saveCuenta(cuenta1);
        cuentaDao.saveCuenta(cuenta2);

        List<Cuenta> cuentas = cuentaDao.findAll();

        assertEquals(2, cuentas.size());
        assertTrue(cuentas.contains(cuenta1));
        assertTrue(cuentas.contains(cuenta2));
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