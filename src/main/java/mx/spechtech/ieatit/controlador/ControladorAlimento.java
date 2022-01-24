package mx.spechtech.ieatit.controlador;

import mx.spechtech.ieatit.modelo.Alimento;
import mx.spechtech.ieatit.modelo.Categoria;
import mx.spechtech.ieatit.repositorio.RepositorioAlimento;
import mx.spechtech.ieatit.repositorio.RepositorioCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import mx.spechtech.ieatit.servicio.ServicioAutenticacion;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

@Controller
@RequestMapping(path = "/alimentos")
public class ControladorAlimento {
    @Autowired
    private RepositorioAlimento repositorioAlimento;
    @Autowired
    private RepositorioCategoria repositorioCategoria;

    @Autowired
    private ServicioAutenticacion servicioAutenticacion;

    @GetMapping(path = "/crear")
    public String crearAlimento(Model model) {
        model.addAttribute("alimento", new Alimento());
        model.addAttribute("title", "Crear alimento");
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        model.addAttribute("categorias", repositorioCategoria.findAll());
        return "alimentos/crear";
    }

    @PostMapping(path = "/crear")
    public ModelAndView crearAlimento(@ModelAttribute Alimento alimento, Model model,
                                      @RequestParam("img") MultipartFile img,
                                      RedirectAttributes redirectAttributes) throws IOException {
        String nombreImagen = img.getOriginalFilename();
        alimento.setImagen(nombreImagen);
        alimento.setDisponible(true);
        repositorioAlimento.save(alimento);

        String directoryName = "target/classes/static/imgs";
        File directory = new File(directoryName);
        if(!directory.exists()) {
            directory.mkdirs();
        }
        File upload = new File(directoryName + "/" + nombreImagen);
        upload.createNewFile();
        FileOutputStream fout = new FileOutputStream(upload);
        fout.write(img.getBytes());
        fout.close();

        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "success");
        redirectAttributes.addFlashAttribute("alertHeading", "Alimento creado con éxito");
        redirectAttributes.addFlashAttribute("alertText", "El alimento " + alimento.getNombre() +
                        " ha sido agregado a la lista de alimentos.");

        return new ModelAndView("redirect:/alimentos/listar");
    }

    @GetMapping(path="/listar")
    public String listarAlimentos(Model model) {

        model.addAttribute("alimentos", repositorioAlimento.findByDisponible(true));
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        model.addAttribute("title", "Alimentos");
        return "alimentos/listar";
    }

    @GetMapping(path="/actualizar/{id}")
    public String listarAlimentos(@PathVariable("id") int id, Model model) {
        Alimento alimento = repositorioAlimento.findById(id).get();
        model.addAttribute("alimento", alimento);
        model.addAttribute("title", "Actualizar alimento");
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        model.addAttribute("categorias", repositorioCategoria.findAll());
        model.addAttribute("currentCategoryId", alimento.getCategoria().getId());
        return "alimentos/actualizar";
    }

    @PostMapping(path="/actualizar")
    public ModelAndView actualizarAlimento(
        @RequestParam int id,
        @ModelAttribute Alimento actualizacion,
        RedirectAttributes redirectAttributes) {
        Alimento alimento = repositorioAlimento.findById(id).get();
        alimento.setNombre(actualizacion.getNombre());
        alimento.setPrecio(actualizacion.getPrecio());
        alimento.setDescripcion(actualizacion.getDescripcion());
        alimento.setCategoria(actualizacion.getCategoria());
        repositorioAlimento.save(alimento);

        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "info");
        redirectAttributes.addFlashAttribute("alertHeading", "Alimento actualizado con éxito");
        redirectAttributes.addFlashAttribute("alertText", "El alimento " + alimento.getNombre() +
                " ha sido actualizado.");

        return new ModelAndView("redirect:/alimentos/listar");
    }

    @PostMapping(path="/eliminar", headers="Content-Type=application/json")
    public String eliminarAlimento(@RequestBody String id, RedirectAttributes redirectAttributes) {
        int idInt = Integer.parseInt(id.replace("\"", ""));
        Alimento alimento = repositorioAlimento.findById(idInt).get();
        alimento.setDisponible(false);
        repositorioAlimento.save(alimento);

        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "danger");
        redirectAttributes.addFlashAttribute("alertHeading", "Alimento eliminado con éxito");
        redirectAttributes.addFlashAttribute("alertText", "El alimento " + alimento.getNombre() +
                " ha sido eliminado de la lista de alimentos.");
        redirectAttributes.addFlashAttribute("usuario", servicioAutenticacion.usuarioActual());
        return "redirect:/alimentos/listar";
    }
}
