package mx.spechtech.ieatit.repositorio;

import mx.spechtech.ieatit.modelo.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioCategoria extends CrudRepository<Categoria, Integer> {
}
