package mx.spechtech.ieatit.servicio;

import mx.spechtech.ieatit.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class ServicioNotificaciones {
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    public ServicioNotificaciones(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void notificarRegistroRepartidor(Usuario usuario, String rawPassword) throws MailException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(usuario.getEmail());
        simpleMailMessage.setFrom(senderEmail);
        simpleMailMessage.setSubject("Bienvenido repartidor!");
        String text = "Bienvenido al equipo de repartidores.\n Para acceder usa la siguiente contraseña: " + rawPassword;
        simpleMailMessage.setText(text);

        javaMailSender.send(simpleMailMessage);
    }
}
