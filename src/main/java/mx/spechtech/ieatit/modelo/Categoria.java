package mx.spechtech.ieatit.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
// @Table(schema="ieatit", name="categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // @Column(name="IdCategoria", table="categoria")
    private int id;
    // @Column(name="Nombre", table="categoria")
    private String nombre;
    // @Column(name="Descripcion", table="categoria")
    private String descripcion;
    @OneToMany(mappedBy = "categoria")
    private List<Alimento> alimentos;

    public Categoria() {}

    public Categoria(int id, String nombre, String descripcion, List<Alimento> alimentos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.alimentos = alimentos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Alimento> getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(List<Alimento> alimentos) {
        this.alimentos = alimentos;
    }
}
