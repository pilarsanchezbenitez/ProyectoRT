package mx.spechtech.ieatit.repositorio;

import mx.spechtech.ieatit.modelo.Estado;
import mx.spechtech.ieatit.modelo.Orden;
import mx.spechtech.ieatit.modelo.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepositorioOrden extends CrudRepository<Orden, String> {
    List<Orden> findByEstadoAndRepartidor(Estado estado, Usuario usuario);
    List<Orden> findByEstado(Estado estado);
}
