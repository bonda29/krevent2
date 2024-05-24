package org.example.krevent.service.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
}