package com.carrental.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Data
public class CarDto {

    private Long id;
    private String brand;
    private String name;
    private String type;
    private String transmission;
    private String color;
    private Date year;
    private String description;
    private Long price;
    private MultipartFile img;
    private byte[] returnedImg;

}
