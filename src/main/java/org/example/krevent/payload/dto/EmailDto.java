package org.example.krevent.payload.dto;

import lombok.Data;
import lombok.Value;

import java.awt.image.BufferedImage;
import java.util.List;

@Data
public class EmailDto {
    private String to;
    private String subject;
    private String name;
    private Integer numberOfTickets;
    private Double price;
    private List<BufferedImage> tickets;
}
