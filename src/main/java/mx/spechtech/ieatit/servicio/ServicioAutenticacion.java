package mx.spechtech.ieatit.servicio;

import mx.spechtech.ieatit.modelo.Usuario;

public interface ServicioAutenticacion {
    void autoLogin(String email, String password);

    Usuario usuarioActual();
}
