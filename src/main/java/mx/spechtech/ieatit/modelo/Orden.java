package mx.spechtech.ieatit.modelo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Orden {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String idOrden;
    private Date horaCreacion;
    private Date horaDeEntregaAproximada;
    @Enumerated(EnumType.STRING)
    private Estado estado;
    private double costoTotal;
    @ManyToOne
    private Direccion direccionDeEntrega;
    @ManyToOne
    private Usuario usuario;
    @ManyToMany
    private List<Alimento> alimentos;
    @ManyToOne
    private Usuario repartidor;

    public Orden() {
    }

    public Orden(List<Alimento> alimentos, Usuario usuario, Direccion direccionDeEntrega) {
        this.alimentos = alimentos;
        this.usuario = usuario;
        this.direccionDeEntrega = direccionDeEntrega;
        horaCreacion = new Date();
        asignarHoraDeEntrega();
        estado = Estado.PENDIENTE;
        calculaCostoTotal();
    }

    private void asignarHoraDeEntrega() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, 30);
        horaDeEntregaAproximada = calendar.getTime();
    }

    private void calculaCostoTotal() {
        costoTotal = 0;
        for (Alimento a : alimentos) {
            costoTotal += a.getPrecio();
        }
    }

    public String getIdOrden() {
        return idOrden;
    }

    public Date getHoraCreacion() {
        return horaCreacion;
    }

    public Date getHoraDeEntregaAproximada() {
        return horaDeEntregaAproximada;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public Direccion getDireccionDeEntrega() {
        return direccionDeEntrega;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public List<Alimento> getAlimentos() {
        return alimentos;
    }

    public Usuario getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Usuario repartidor) {
        this.repartidor = repartidor;
    }

    public Estado getSiguienteEstado() {
        if(estado == Estado.PENDIENTE) {
            return Estado.LISTA;
        } else if(estado == Estado.LISTA) {
            return Estado.EN_CAMINO;
        } else {
            return Estado.ENTREGADA;
        }
    }
}
