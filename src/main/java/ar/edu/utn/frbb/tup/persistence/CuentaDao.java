package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cuenta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaDao extends JpaRepository<Cuenta, Integer> {

    Cuenta findByNumeroCuenta(long numeroCuenta);

    List<Cuenta> findAll();

    @Modifying
    @Transactional
    @Query("DELETE FROM Cuenta c WHERE c.numeroCuenta = :numeroCuenta")
    void deleteByNumeroCuenta(@Param("numeroCuenta") long numeroCuenta);
}
