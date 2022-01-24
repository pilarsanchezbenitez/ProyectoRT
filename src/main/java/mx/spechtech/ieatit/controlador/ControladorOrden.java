package mx.spechtech.ieatit.controlador;

import mx.spechtech.ieatit.modelo.*;
import mx.spechtech.ieatit.repositorio.RepositorioAlimento;
import mx.spechtech.ieatit.repositorio.RepositorioDireccion;
import mx.spechtech.ieatit.repositorio.RepositorioOrden;
import mx.spechtech.ieatit.servicio.ServicioAutenticacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Controller
@RequestMapping(path = "/orden")
public class ControladorOrden {
    @Autowired
    private RepositorioOrden repositorioOrden;
    @Autowired
    RepositorioAlimento repositorioAlimento;
    @Autowired
    ServicioAutenticacion servicioAutenticacion;

    /**
     * Devuelve la vista con los alimentos disponibles para añadirlos a una orden nueva
     *
     * @param model
     * @return
     */
    @GetMapping(path = "crear")
    public String crearOrden(Model model) {
        Iterable<Alimento> alimentos = repositorioAlimento.findAll();
        model.addAttribute("alimentos", alimentos);
        model.addAttribute("title", "Crear orden");
        return "orden/crear";
    }

    /**
     * Almacena la orden con los alimentos con id en idAlimentos
     *
     * @param
     * @return
     */
    @PostMapping(path="/crear", headers="Content-Type=application/json")
    public String crearOrden(@RequestBody String alimentos) {
        ArrayList<String> idAlimentos = getIds(alimentos);
        Usuario usuario = servicioAutenticacion.usuarioActual();
        Direccion direccion = usuario.getDireccion();
        List<Alimento> listaAlimentos = new LinkedList<>();
        for (String idAlimento : idAlimentos) {
            Optional<Alimento> alimento = repositorioAlimento.findById(Integer.parseInt(idAlimento));
            alimento.ifPresent(listaAlimentos::add);
        }
        Orden orden = new Orden(listaAlimentos, usuario, direccion);
        repositorioOrden.save(orden);

        return "redirect:/orden/detalles/" + orden.getIdOrden();
    }

    ArrayList<String> getIds(String alimentos) {
        ArrayList<String> ids = new ArrayList<>();
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(alimentos);
        while (m.find())
            ids.add(m.group(1));
        return ids;
    }

    /**
     * Regresa la vista mostrando la orden con id idOrden
     *
     * @param idOrden Id de la orden que se muestra en la vista
     * @param model   Modelo para añadir los datos
     * @return
     */
    @GetMapping(path = "/detalles/{idOrden}")
    public String detallesOrden(@PathVariable String idOrden, Model model) {
        Optional<Orden> orden = repositorioOrden.findById(idOrden);
        model.addAttribute("orden", orden.get());
        model.addAttribute("title", "Detalles de orden");
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        return "orden/detalles";
    }

    @GetMapping(path = "/mostrar")
    public String mostrarOrdenes(@RequestParam(required = false) Estado estado, Model model) {
        Usuario usuario = servicioAutenticacion.usuarioActual();
        model.addAttribute("usuario", usuario);
        if(usuario.esCliente()) {
            model.addAttribute("ordenes", usuario.getOrdenes());
        } else if(usuario.esAdmin()) {
            model.addAttribute("ordenes", repositorioOrden.findByEstado(Estado.PENDIENTE));
        } else if(usuario.esRepartidor()) {
            estado = (estado == null) ? Estado.LISTA : estado;
            if(estado == Estado.LISTA) {
                model.addAttribute("ordenes", repositorioOrden.findByEstado(estado));
            } else {
                model.addAttribute("ordenes", repositorioOrden.findByEstadoAndRepartidor(estado, usuario));
            }
        }
        return "/orden/listado";
    }

    @PostMapping(path = "/estado")
    public String cambiarEstado(@RequestParam Estado estado, @RequestParam String idOrden,
                                RedirectAttributes redirectAttributes) {
        Orden orden = (repositorioOrden.findById(idOrden)).get();
        if(orden.getEstado() == Estado.LISTA) {
            orden.setRepartidor(servicioAutenticacion.usuarioActual());
        }
        orden.setEstado(estado);
        repositorioOrden.save(orden);
        String redirectAEnCamino = orden.getEstado() == Estado.ENTREGADA ? "?estado=EN_CAMINO" : "";

        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "info");
        redirectAttributes.addFlashAttribute("alertHeading", "Orden actualizada con éxito");
        redirectAttributes.addFlashAttribute("alertText", "Cambiaste el estado de la orden a " + orden.getEstado() +
                " exitosamente.");

        return "redirect:/orden/mostrar" + redirectAEnCamino;
    }
}
