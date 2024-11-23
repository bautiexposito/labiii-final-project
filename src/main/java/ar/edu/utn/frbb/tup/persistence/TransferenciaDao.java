package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransferenciaDao extends JpaRepository<Transferencia, Integer> {

    List<Transferencia> findAll();

    @Query("SELECT t FROM Transferencia t WHERE t.cuentaOrigen = :numeroCuenta OR t.cuentaDestino = :numeroCuenta")
    List<Transferencia> findTransferenciasByCuenta(@Param("numeroCuenta") long numeroCuenta);
}
