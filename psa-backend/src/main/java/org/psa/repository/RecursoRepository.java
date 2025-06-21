package org.psa.repository;

import org.psa.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, String> {
    
    Recurso findByDni(String dni);
    List<Recurso> findByActivoTrue();
    List<Recurso> findByRolId(String rolId);
    
    @Query("SELECT r FROM Recurso r WHERE r.nombre LIKE %?1% OR r.apellido LIKE %?1%")
    List<Recurso> buscarPorNombreOApellido(String termino);
    
    @Query("SELECT COUNT(r) FROM Recurso r WHERE r.activo = true")
    long contarRecursosActivos();
}
