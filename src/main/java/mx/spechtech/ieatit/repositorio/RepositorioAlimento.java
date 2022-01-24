package mx.spechtech.ieatit.repositorio;

import mx.spechtech.ieatit.modelo.Alimento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepositorioAlimento extends CrudRepository<Alimento, Integer> {
    List<Alimento> findByDisponible(Boolean disponible);
}
