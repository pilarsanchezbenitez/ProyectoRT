package mx.spechtech.ieatit.servicio.implementacion;

import mx.spechtech.ieatit.modelo.Direccion;
import mx.spechtech.ieatit.modelo.Role;
import mx.spechtech.ieatit.modelo.Usuario;
import mx.spechtech.ieatit.repositorio.RepositorioDireccion;
import mx.spechtech.ieatit.repositorio.RepositorioUsuario;
import mx.spechtech.ieatit.servicio.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioUsuarioImpl implements ServicioUsuario {
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioDireccion repositorioDireccion;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private void save(Usuario usuario, Role rol) {
        usuario.setContrasenia(passwordEncoder.encode(usuario.getContrasenia()));
        usuario.setRole(rol);
        repositorioUsuario.save(usuario);
    }

    public void guardarCliente(Usuario usuario, Direccion direccion) {
        save(usuario, Role.CLIENTE);
        direccion.setUsuario(usuario);
        repositorioDireccion.save(direccion);
    }

    public void guardarRepartidor(Usuario usuario, Direccion direccion) {
        save(usuario, Role.REPARTIDOR);
        direccion.setUsuario(usuario);
        repositorioDireccion.save(direccion);
    }
}
