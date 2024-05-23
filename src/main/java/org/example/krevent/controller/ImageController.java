package org.example.krevent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qr-codes")
public class ImageController {
    @Value("${application.qr-code.path}")
    private String IMAGES_DIR;

    @GetMapping("/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        Path imgPath = Paths.get(IMAGES_DIR + imageName);

        if (!Files.exists(imgPath)) {
            throw new RuntimeException("Image not found");
        }

        byte[] bytes = Files.readAllBytes(imgPath);
        String contentType = Files.probeContentType(imgPath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(bytes);
    }
}