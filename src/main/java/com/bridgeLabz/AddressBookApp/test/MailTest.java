import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.SimpleMailMessage;

import java.util.Properties;

public class MailTest {
    public static void main(String[] args) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("divyanshshuklaofficial23@gmail.com");
        mailSender.setPassword("ipjwytcqxmxxyphf");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // Enable detailed logs

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("divyanshshuklaofficial23@gmail.com");
        message.setTo("divyansh.shukla_cs21@gla.ac.in");
        message.setSubject("Test Email from Divyansh's App 🚀");
        message.setText("This is a test email from your Spring Boot app!");

        try {
            mailSender.send(message);
            System.out.println("✅ Test email sent successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error Sending Email: " + e.getMessage());
        }
    }
}
