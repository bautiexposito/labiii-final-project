package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.controller.dto.CuentaDto;
import ar.edu.utn.frbb.tup.model.Cuenta;
import ar.edu.utn.frbb.tup.model.TipoCuenta;
import ar.edu.utn.frbb.tup.model.exception.CuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.model.exception.TipoCuentaAlreadyExistsException;
import ar.edu.utn.frbb.tup.persistence.CuentaDao;
import ar.edu.utn.frbb.tup.service.impl.ClienteServiceImpl;
import ar.edu.utn.frbb.tup.service.impl.CuentaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaServiceTest {

    @Mock
    private CuentaDao cuentaDao;

    @Mock
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDarDeAltaCuenta() throws Exception, TipoCuentaAlreadyExistsException, CuentaAlreadyExistsException {
        CuentaDto cuentaDto = getCuentaDto();

        when(cuentaDao.findCuenta(anyLong())).thenReturn(null);
        when(cuentaDao.saveCuenta(any(Cuenta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cuenta cuenta = cuentaService.darDeAltaCuenta(cuentaDto);

        assertNotNull(cuenta);
    }

    @Test
    public void testDarDeAltaCuenta_ErrorCuentaYaExiste() {
        CuentaDto cuentaDto = getCuentaDto();

        when(cuentaDao.findCuenta(anyLong())).thenReturn(new Cuenta());

        assertThrows(CuentaAlreadyExistsException.class, () -> cuentaService.darDeAltaCuenta(cuentaDto));
    }

    @Test
    public void testTipoCuentaEstaSoportada() {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);
        cuenta.setTipoCuenta(TipoCuenta.CUENTA_CORRIENTE);
        assertTrue(cuentaService.tipoCuentaEstaSoportada(cuenta));
    }

    @Test
    public void testTipoCuentaEstaSoportada_False() {
        CuentaDto cuentaDto = getCuentaDto();
        Cuenta cuenta = new Cuenta(cuentaDto);
        cuenta.setTipoCuenta(TipoCuenta.INVERSIONES);
        assertFalse(cuentaService.tipoCuentaEstaSoportada(cuenta));
    }

//    @Test
//    public void testFindByID() {
//        CuentaDto cuentaDto = getCuentaDto();
//        Cuenta cuentaEsperada = new Cuenta(cuentaDto);
//
//        long id = cuentaEsperada.getNumeroCuenta();
//
//        when(cuentaDao.findCuenta(id)).thenReturn(cuentaEsperada);
//
//        Cuenta cuentaObtenida = cuentaService.findByID(id);
//
//        assertEquals(cuentaEsperada, cuentaObtenida);
//    }

//    @Test
//    public void testFindByID_NoExiste() {
//        long id = 90009;
//
//        when(cuentaDao.findCuenta(id)).thenReturn(null);
//
//        Cuenta cuenta = cuentaService.findByID(id);
//
//        assertNull(cuenta);
//    }

    @Test
    public void testFindAll() {
        CuentaDto cuentaDto1 = getCuentaDto();
        Cuenta cuenta1 = new Cuenta(cuentaDto1);
        CuentaDto cuentaDto2 = getCuentaDto();
        cuentaDto2.setDniTitular(43046270);
        Cuenta cuenta2 = new Cuenta(cuentaDto2);
        List<Cuenta> cuentasEsperadas = Arrays.asList(cuenta1,cuenta2);

        when(cuentaDao.findAll()).thenReturn(cuentasEsperadas);

        List<Cuenta> cuentasObtenidas = cuentaService.findAll();

        assertEquals(cuentasEsperadas, cuentasObtenidas);
        verify(cuentaDao, times(1)).findAll();
    }

    @Test
    public void testFindAll_RetornaNull() {
        List<Cuenta> cuentas = Arrays.asList(new Cuenta(), new Cuenta());
        when(cuentaDao.findAll()).thenReturn(cuentas);
        List<Cuenta> result = cuentaService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
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