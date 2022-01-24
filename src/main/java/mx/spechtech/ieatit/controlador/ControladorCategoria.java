package mx.spechtech.ieatit.controlador;

import mx.spechtech.ieatit.modelo.Categoria;
import mx.spechtech.ieatit.repositorio.RepositorioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import mx.spechtech.ieatit.servicio.ServicioAutenticacion;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(path = "/categorias")
public class ControladorCategoria {
    @Autowired
    private RepositorioCategoria repositorioCategoria;

    @Autowired
    private ServicioAutenticacion servicioAutenticacion;

    @GetMapping(path = "/crear")
    public String crearCategoria(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("title", "Crear categoria");
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        return "categorias/crear";
    }

    @PostMapping(path = "/crear")
    public ModelAndView crearCategoria(@ModelAttribute Categoria categoria, RedirectAttributes redirectAttributes) {
        repositorioCategoria.save(categoria);
        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "success");
        redirectAttributes.addFlashAttribute("alertHeading", "Categoria creada con éxito");
        redirectAttributes.addFlashAttribute("alertText", "La categoría " + categoria.getNombre() +
                        " ha sido agregada a la lista de categorías.");
        return new ModelAndView("redirect:/categorias/listar");
    }

    @GetMapping(path="/listar")
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", repositorioCategoria.findAll());
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        model.addAttribute("title", "Categorias");
        return "categorias/listar";
    }

    @GetMapping(path="/actualizar/{id}")
    public String listarCategorias(@PathVariable("id") int id, Model model) {
        model.addAttribute("categoria", repositorioCategoria.findById(id).get());
        model.addAttribute("title", "Actualizar categoria");
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        return "categorias/actualizar";
    }

    @PostMapping(path="/actualizar")
    public ModelAndView actualizarCategoria(
        @RequestParam int id,
        @ModelAttribute Categoria actualizacion,
        RedirectAttributes redirectAttributes
    ) {
        Categoria categoria = repositorioCategoria.findById(id).get();
        categoria.setNombre(actualizacion.getNombre());
        categoria.setDescripcion(actualizacion.getDescripcion());
        repositorioCategoria.save(categoria);

        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "info");
        redirectAttributes.addFlashAttribute("alertHeading", "Categoria actualizada con éxito");
        redirectAttributes.addFlashAttribute("alertText", "La categoría " + categoria.getNombre() +
                " ha sido actualizada.");

        return new ModelAndView("redirect:/categorias/listar");
    }

    @PostMapping(path="/eliminar")
    public ModelAndView eliminarCategoria(@RequestParam int id, RedirectAttributes redirectAttributes) {
        Categoria categoria = repositorioCategoria.findById(id).get();
        repositorioCategoria.deleteById(id);

        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "danger");
        redirectAttributes.addFlashAttribute("alertHeading", "Categoria eliminada");
        redirectAttributes.addFlashAttribute("alertText", "La categoría " + categoria.getNombre() +
                " ha sido eliminada de la lista de categorías.");

        return new ModelAndView("redirect:/categorias/listar");
    }
}
