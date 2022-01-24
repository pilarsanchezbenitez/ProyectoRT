package mx.spechtech.ieatit.modelo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    @Id
    private String email;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String contrasenia;
    private String telefono;
    @Transient
    private String confContrasenia;
    private Role role;
    @OneToOne(mappedBy = "usuario")
    private Direccion direccion;
    @OneToMany(mappedBy = "usuario")
    private List<Orden> ordenes;
    @OneToMany(mappedBy = "repartidor")
    private List<Orden> ordenesAsignadas;

    public Usuario() {
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

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getConfContrasenia() {
        return confContrasenia;
    }

    public void setConfContrasenia(String confContrasenia) {
        this.confContrasenia = confContrasenia;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Orden> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }

    public List<Orden> getOrdenesAsignadas() {
        return ordenesAsignadas;
    }

    public void setOrdenesAsignadas(List<Orden> ordenesAsignadas) {
        this.ordenesAsignadas = ordenesAsignadas;
    }

    public boolean esCliente() {
        return role == Role.CLIENTE;
    }

    public boolean esAdmin() {
        return role == Role.ADMINISTRADOR;
    }

    public boolean esRepartidor() {
        return role == Role.REPARTIDOR;
    }
}
