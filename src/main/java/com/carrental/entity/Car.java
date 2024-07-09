package com.carrental.entity;

import com.carrental.dto.CarDto;
import lombok.Data;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "cars")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String name;

    private String type;

    private String transmission;

    private String color;

    private Date year;

    private Long price;

    @Lob
    @Column(name = "description")
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    public void getEntity(CarDto carDto) {
        this.brand = carDto.getBrand();
        this.name = carDto.getName();
        this.type = carDto.getType();
        this.transmission = carDto.getTransmission();
        this.color = carDto.getColor();
        this.year = carDto.getYear();
        this.price = carDto.getPrice();
        this.description = carDto.getDescription();
    }

    public CarDto getCarDto() {
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setName(name);
        carDto.setBrand(brand);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setColor(color);
        carDto.setYear(year);
        carDto.setDescription(description);
        carDto.setPrice(price);
        carDto.setReturnedImg(img);
        return carDto;
    }

}
