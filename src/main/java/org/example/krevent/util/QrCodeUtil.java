package org.example.krevent.util;

import io.nayuki.qrcodegen.QrCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class QrCodeUtil {
    private final static int imageSize = 500;

    public static BufferedImage toImage(QrCode qr) {
        return toImage(qr, 10, 4, 0xFFFFFF, 0x000000);
    }

    public static BufferedImage toImage(QrCode qr, int scale, int border, int lightColor, int darkColor) {
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
        return resizeImage(result);
    }

    private static BufferedImage resizeImage(BufferedImage originalImage) {
        Image tmp = originalImage.getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }
}
