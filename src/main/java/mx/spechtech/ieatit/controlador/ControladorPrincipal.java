package mx.spechtech.ieatit.controlador;

import mx.spechtech.ieatit.formulario.FormRegistro;
import mx.spechtech.ieatit.modelo.Usuario;
import mx.spechtech.ieatit.servicio.ServicioAutenticacion;
import mx.spechtech.ieatit.servicio.ServicioNotificaciones;
import mx.spechtech.ieatit.servicio.ServicioUsuario;
import mx.spechtech.ieatit.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ControladorPrincipal {
    @Autowired
    private ServicioUsuario servicioUsuario;

    @Autowired
    private ServicioAutenticacion servicioAutenticacion;

    @Autowired
    private ServicioNotificaciones servicioNotificaciones;

    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        model.addAttribute("title", "iEatIt");
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login")
    public String login(Model model) {
        if(servicioAutenticacion.usuarioActual() != null)
            return "redirect:/home";
        model.addAttribute("title", "Iniciar sesión");
        return "auth/login";
    }

    @GetMapping("/registrar")
    public String registrar(Model model) {
        if(servicioAutenticacion.usuarioActual() != null)
            return "redirect:/home";
        model.addAttribute("formRegistro", new FormRegistro());
        model.addAttribute("title", "Crear cuenta");
        return "auth/registrar";
    }

    @PostMapping("/registrar")
    public String registrar(@ModelAttribute FormRegistro formRegistro) {
        Usuario usuario = formRegistro.buildUsuario();
        servicioUsuario.guardarCliente(usuario, formRegistro.buildDireccion());
        servicioAutenticacion.autoLogin(usuario.getEmail(), usuario.getContrasenia());
        return "redirect:/home";
    }


    @GetMapping("/administrador/registrar")
    public String registrarRepartidor(Model model) {
        model.addAttribute("usuario", servicioAutenticacion.usuarioActual());
        model.addAttribute("formRegistro", new FormRegistro());
        model.addAttribute("title", "Crear cuenta");
        return "administrador/registrar-repartidor";
    }

    @PostMapping("/administrador/registrar")
    public String registrarRepartidor(@ModelAttribute FormRegistro formRegistro, RedirectAttributes redirectAttributes) {
        Usuario usuario = formRegistro.buildUsuario();
        String password = RandomString.randomAlphaNumeric(5);
        usuario.setContrasenia(password);
        servicioUsuario.guardarRepartidor(usuario, formRegistro.buildDireccion());
        try {
            servicioNotificaciones.notificarRegistroRepartidor(usuario, password);
        } catch (MailException e) {
            System.out.println("*****\nError al mandar notificación.\n" + e + "*****");
        }

        redirectAttributes.addFlashAttribute("isAlert", true);
        redirectAttributes.addFlashAttribute("alertType", "success");
        redirectAttributes.addFlashAttribute("alertHeading", "Repartidor agregado con éxito");
        redirectAttributes.addFlashAttribute("alertText", "El repartidor con email " + usuario.getEmail() +
                " ha sido agregado a la lista de repartidores.");

        return "redirect:/administrador/registrar";
    }

    @GetMapping("/cliente/index")
    public String clienteIndex() {
        return "cliente/index";
    }

    @GetMapping("/administrador/index")
    public String administradorIndex() {
        return "administrador/index";
    }

    @GetMapping("/repartidor/index")
    public String repartidorIndex() {
        return "repartidor/index";
    }
}
