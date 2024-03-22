package controllers.Back.StockMateriel;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailService {

    public static void sendEmail(String recipient, String subject, String body) throws MessagingException {
        // Paramètres SMTP
        String host = "smtp.example.com"; // Remplacez par le serveur SMTP approprié
        String username = "your-email@example.com"; // Remplacez par votre adresse e-mail
        String password = "your-password"; // Remplacez par votre mot de passe
        int port = 587; // Le port SMTP

        // Configuration des propriétés
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Création de la session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);

            // Envoi du message
            Transport.send(message);

            System.out.println("E-mail sent successfully!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}