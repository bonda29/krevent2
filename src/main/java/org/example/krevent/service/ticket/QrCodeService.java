package org.example.krevent.service.ticket;

import io.nayuki.qrcodegen.QrCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static io.nayuki.qrcodegen.QrCode.Ecc.LOW;
import static org.example.krevent.util.QrCodeUtil.toImage;

@Service
@RequiredArgsConstructor
public class QrCodeService {
    public String createQrCode(String imageName, String text) throws IOException {
        QrCode qr = QrCode.encodeText(text, LOW);                                               // Make the QR Code symbol
        BufferedImage img = toImage(qr);                                                        // Convert to bitmap image
        File imgFile = new File("src/main/resources/static/" + imageName + ".png");   // Filepath for output
        ImageIO.write(img, "png", imgFile);                                         // Write image to file

        return "http://localhost:8080/" + imageName + ".png";
    }

}
