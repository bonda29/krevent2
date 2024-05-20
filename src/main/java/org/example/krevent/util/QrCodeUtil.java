package org.example.krevent.util;

import io.nayuki.qrcodegen.QrCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static io.nayuki.qrcodegen.QrCode.Ecc.LOW;

public class QrCodeUtil {
    private static BufferedImage toImage(QrCode qr) {
        return toImage(qr, 10, 4, 0xFFFFFF, 0x000000);
    }

    private static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
        Objects.requireNonNull(qr);
        if (scale <= 0 || border < 0)
            throw new IllegalArgumentException("Value out of range");
        if (border > Integer.MAX_VALUE / 2 || qr.size + border * 2L > Integer.MAX_VALUE / scale)
            throw new IllegalArgumentException("Scale or border too large");

        BufferedImage result = new BufferedImage((qr.size + border * 2) * scale, (qr.size + border * 2) * scale, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                boolean color = qr.getModule(x / scale - border, y / scale - border);
                result.setRGB(x, y, color ? darkColor : lightColor);
            }
        }
        return result;
    }

    public static String createQrCode(String imageName, String text) throws IOException {
        QrCode qr = QrCode.encodeText(text, LOW);  // Make the QR Code symbol
        BufferedImage img = toImage(qr);          // Convert to bitmap image
        File imgFile = new File("src/main/resources/static/" + imageName + ".png");   // Filepath for output
        ImageIO.write(img, "png", imgFile); // Write image to file

        return "http://localhost:8080/" + imageName + ".png";
    }
}
