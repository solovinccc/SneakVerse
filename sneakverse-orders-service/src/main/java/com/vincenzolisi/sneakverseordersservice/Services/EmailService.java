package com.vincenzolisi.sneakverseordersservice.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderConfirmation(String toEmail, int orderId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vincenzolisi12@gmail.com");
        message.setTo(toEmail);
        message.setSubject("SNEAKVERSE - Ordine #" + orderId);
        message.setText("SNEAKVERSE\nAbbiamo preso in carico il tuo ordine!\n\n" +
                "Il nostro team sta preparando il tuo pacco. Riceverai a momenti una mail automatica per la pianificazione della consegna.\n\n " +
                "Il team di SneakVerse");
        mailSender.send(message);
        System.out.println("EMAIL 1(Conferma Ordine) inviata a : " + toEmail);
    }

    public void sendCourierChoiceEmail(String toEmail, int orderId) {
        try {

            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = new org.springframework.mail.javamail.MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("vincenzolisi12@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("CORRIERE EXPRESS - Pianifica la consegna (Ordine #" + orderId + ")");

            String link24h = "http://sneakverse.it/api/internal/orders/" + orderId + "/schedule?days=1";
            String link48h = "http://sneakverse.it/api/internal/orders/" + orderId + "/schedule?days=2";

            String htmlMsg = "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: 0 auto; border: 1px solid #eee; padding: 20px; border-radius: 8px;'>"
                    + "<h2 style='color: #FF5722; text-align: center;'>Il tuo pacco è in viaggio!</h2>"
                    + "<p style='font-size: 16px;'>Gentile cliente,</p>"
                    + "<p style='font-size: 16px;'>Il tuo ordine <strong>SneakVerse #" + orderId + "</strong> è stato preso in carico dalla nostra logistica.</p>"
                    + "<p style='font-size: 16px;'>Quando preferisci ricevere la consegna?</p>"
                    + "<div style='text-align: center; margin-top: 30px; margin-bottom: 30px;'>"
                    + "<a href='" + link24h + "' style='background-color: #4CAF50; color: white; padding: 14px 25px; text-decoration: none; display: inline-block; border-radius: 5px; font-weight: bold; font-size: 16px; margin: 10px;'>Entro 24h</a>"
                    + "<a href='" + link48h + "' style='background-color: #2196F3; color: white; padding: 14px 25px; text-decoration: none; display: inline-block; border-radius: 5px; font-weight: bold; font-size: 16px; margin: 10px;'>Entro 48h</a>"
                    + "</div>"
                    + "<hr style='border: 0; border-top: 1px solid #eee;'>"
                    + "<p style='font-size: 12px; color: #777; text-align: center;'>Grazie per averci scelto.<br><strong>Il team di Corriere Express</strong></p>"
                    + "</div>";

            helper.setText(htmlMsg, true);

            mailSender.send(message);
            System.out.println("Email 2 (Scelta Corriere) inviata a: " + toEmail);

        } catch (Exception e) {
            System.err.println("Errore durante l'invio della mail HTML: " + e.getMessage());
        }
    }

    public void sendDeliveryConfirmation(String toEmail, int orderId) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vincenzolisi12@gmail.com");
        message.setTo(toEmail);
        message.setSubject("SNEAKVERSE - Ordine Consegnato! (identificativo: #" + orderId + ")");
        message.setText("Gentile utente, \n\n" +
                "Ti confermiamo che il tuo ordine #" + orderId + " è stato consegnato.");
        mailSender.send(message);
        System.out.println("Email 3 (Consegna Avvenuta) inviata a : " + toEmail);
    }

}
