package ar.edu.utn.frbb.tup.service;

import java.util.Random;

public class Banelco {

    // Simulamos una invocacion a un servicio externo "Banelco",
    // cuando la cuentaDestino no esta creada en nuestro sistema bancario.
    // 1 de cada 10 veces, podemos simular que la cuentaDestino existe.
    public boolean cuentaExiste(long cuentaDestino){
        Random random = new Random();
        int randomNumber = random.nextInt(10);
        return randomNumber == 0;
    }
}
