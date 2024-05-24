package org.example.krevent.service.ticket;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.krevent.payload.dto.EmailDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String from;


    public void sendEmail(EmailDto data) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(from);
            helper.setTo(data.getTo());
            helper.setSubject(data.getSubject());

            Context context = new Context();
            context.setVariable("name", data.getName());
            context.setVariable("numberOfTickets", data.getNumberOfTickets());
            context.setVariable("price", data.getPrice());

            String content = templateEngine.process("purchaseConfirmation", context);
            helper.setText(content, true);

            // Attach each ticket image
            for (int i = 0; i < data.getTickets().size(); i++) {
                BufferedImage ticket = data.getTickets().get(i);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(ticket, "png", bos);
                ByteArrayResource image = new ByteArrayResource(bos.toByteArray());
                helper.addAttachment("ticket" + (i + 1) + ".jpg", image);
            }

            javaMailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}