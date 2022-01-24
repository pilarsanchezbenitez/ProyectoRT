package mx.spechtech.ieatit.servicio.implementacion;

import mx.spechtech.ieatit.modelo.Usuario;
import mx.spechtech.ieatit.repositorio.RepositorioUsuario;
import mx.spechtech.ieatit.servicio.ServicioAutenticacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacionImpl implements ServicioAutenticacion {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    RepositorioUsuario repositorioUsuario;

    @Override
    public void autoLogin(String email, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Override
    public Usuario usuarioActual() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repositorioUsuario.findByEmail(email);
    }
}
