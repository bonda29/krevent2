package org.example.krevent.service.ticket;

import lombok.RequiredArgsConstructor;
import org.example.krevent.models.enums.SeatType;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class TicketImageGenerator {


    public BufferedImage generateTicketImageFromTemplate(String name, SeatType type, Double price, String seat, String qrCodeUrl) {
        try {
            BufferedImage image;
            if (type.toString().contains("BALCONY"))
                image = ImageIO.read(new File("src/main/resources/templates/balconyTicket.png"));
            else
                image = ImageIO.read(new File("src/main/resources/templates/parterTicket.png"));

            Font mainFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Monotype-Corsiva.ttf"));
            Font backFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat-Regular.ttf"));

            // Create a new image
            Graphics2D g = image.createGraphics();

            // Load the QR code image
            BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeUrl));

            // Draw the QR code image
            g.drawImage(qrCodeImage, 122, 77, null);

            // Set the font and color
            float fontSize = 50;
            g.setFont(mainFont.deriveFont(fontSize));
            g.setColor(new Color(103, 205, 228));

            // Draw the name and seat
            g.drawString(name, 900, 82 + fontSize);
            g.drawString(price.toString(), 945, 82 + 50 + 35 + fontSize);
            g.drawString("19:00", 880, 82 + 50 + 35 + 50 + 35 + fontSize);

            fontSize = 25;
            g.setFont(backFont.deriveFont(fontSize));
            g.setColor(new Color(31, 30, 34));
            g.drawString(seat, 336, 487);

            g.dispose();

            return image;
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
}