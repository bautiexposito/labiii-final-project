package ar.edu.utn.frbb.tup.service;

import ar.edu.utn.frbb.tup.persistence.TransferenciaDao;
import ar.edu.utn.frbb.tup.service.impl.TransferenciaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TransferenciaServiceTest {

    @Mock
    TransferenciaDao transferenciaDao;

    @InjectMocks
    TransferenciaServiceImpl transferenciaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}