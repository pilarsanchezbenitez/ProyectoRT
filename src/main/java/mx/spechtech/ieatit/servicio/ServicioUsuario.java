package mx.spechtech.ieatit.servicio;

import mx.spechtech.ieatit.modelo.Direccion;
import mx.spechtech.ieatit.modelo.Usuario;

public interface ServicioUsuario {
    void guardarCliente(Usuario usuario, Direccion direccion);
    void guardarRepartidor(Usuario usuario, Direccion direccion);
}
