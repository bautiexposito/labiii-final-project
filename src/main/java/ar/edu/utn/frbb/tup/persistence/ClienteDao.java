package ar.edu.utn.frbb.tup.persistence;

import ar.edu.utn.frbb.tup.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteDao extends JpaRepository<Cliente, Integer> {

    Cliente findByDni(long dni);

    List<Cliente> findAll();

    @Query("SELECT c FROM Cliente c WHERE c.nombre LIKE %:nombre% OR c.apellido LIKE %:apellido%")
    List<Cliente> findByNombreOrApellido(@Param("nombre") String nombre, @Param("apellido") String apellido);

    @Modifying
    @Query("DELETE FROM Cliente c WHERE c.dni = :dni")
    void deleteByDni(@Param("dni") long dni);
}
