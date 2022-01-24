package mx.spechtech.ieatit.formulario;

import mx.spechtech.ieatit.modelo.Direccion;
import mx.spechtech.ieatit.modelo.Usuario;

public class FormRegistro {
    private String email;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String contrasenia;
    private String telefono;
    private String confContrasenia;
    private String direccion;

    public FormRegistro() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidPaterno) {
        this.apellidoPaterno = apellidPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getConfContrasenia() {
        return confContrasenia;
    }

    public void setConfContrasenia(String confContrasenia) {
        this.confContrasenia = confContrasenia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario buildUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNombre(nombre);
        usuario.setApellidoPaterno(apellidoPaterno);
        usuario.setApellidoMaterno(apellidoMaterno);
        usuario.setContrasenia(contrasenia);
        usuario.setConfContrasenia(confContrasenia);
        return usuario;
    }

    public Direccion buildDireccion() {
        Direccion direccion = new Direccion();
        direccion.setDireccion(this.direccion);
        return direccion;
    }
}
