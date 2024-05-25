package org.example.krevent.service.ticket;

import lombok.RequiredArgsConstructor;
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


    public BufferedImage generateTicketImage(String name, String type, Double price, String seat, String qrCodeUrl) {
        try {
            // Create a new image
            BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = image.createGraphics();

            // Set the background color to white and clear the image
            graphics.setBackground(Color.WHITE);
            graphics.clearRect(0, 0, image.getWidth(), image.getHeight());

            // Load the QR code image
            BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeUrl));

            // Draw the QR code image
            graphics.drawImage(qrCodeImage, 250, 100, null);

            // Set the font and color
            graphics.setFont(new Font("Arial", Font.PLAIN, 20));
            graphics.setColor(Color.BLACK);

            // Draw the name and type
            graphics.drawString("Name: " + name, 50, 650);
            graphics.drawString("Type: " + type, 50, 700);
            graphics.drawString("Seat: " + seat, 50, 750);
            graphics.drawString("Price: " + price, 50, 800);

            graphics.dispose();

            // Return the final image
            return image;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage generateTicketImageFromTemplate(String name, String type, Double price, String seat, String qrCodeUrl) {
        try {
            // Create a new image
            BufferedImage image = ImageIO.read(new File("src/main/resources/templates/ticketTemplate.png"));
            Graphics2D g = image.createGraphics();

            // Load the QR code image
            BufferedImage qrCodeImage = ImageIO.read(new URL(qrCodeUrl));

            // Draw the QR code image
            g.drawImage(qrCodeImage, 122, 42, null);

            // Set the font and color
            Font bigFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat-Black.ttf"));
            float fontSize = 50;
            g.setFont(bigFont.deriveFont(fontSize));
            g.setColor(new Color(248, 212, 64));

            // Draw the name and type
            g.drawString(name, 930, 82 + fontSize);
            g.drawString(price.toString(), 975, 82 + 50 + 35 + fontSize);
            g.drawString("19:00", 910, 82 + 50 + 35 + 50 + 35 + fontSize);

            Font smallFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/main/resources/fonts/Montserrat-Light.ttf"));
            g.setFont(smallFont.deriveFont(30f));
            g.setColor(new Color(31, 30, 34));
            g.drawString(type + "/" + seat, 210, 434 + 20);

            g.dispose();

            // Return the final image
            return image;
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
}