package mx.spechtech.ieatit.repositorio;

import mx.spechtech.ieatit.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioUsuario extends JpaRepository<Usuario, String> {
    Usuario findByEmail(String email);
}
