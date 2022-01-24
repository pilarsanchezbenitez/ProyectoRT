package mx.spechtech.ieatit.servicio.implementacion;

import mx.spechtech.ieatit.modelo.Usuario;
import mx.spechtech.ieatit.repositorio.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = repositorioUsuario.findByEmail(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("No user found with email: " + username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(usuario.getRole().getName()));
        return new User(usuario.getEmail(), usuario.getContrasenia(), grantedAuthorities);
    }
}
