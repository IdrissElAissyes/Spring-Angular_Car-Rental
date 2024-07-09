package com.carrental.controller;

import com.carrental.dto.*;
import com.carrental.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // User can view All Cars

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(customerService.getAllCars());
    }

    //User can view a single car

    @GetMapping("/car/{carId}")
    public ResponseEntity<UserSingleCarDto> getCarByCarId(@PathVariable("carId") Long carId) {
        return ResponseEntity.ok(customerService.getCarById(carId));
    }

    //User can Book a car

    @PostMapping("/car/book/{carId}")
    public ResponseEntity<Void> bookACar(@PathVariable Long carId, @RequestBody BookCarDto bookCarDto) {
        boolean success = customerService.bookACar(carId, bookCarDto);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //Get all cars that booked by the user

    @GetMapping("/car/booked/{userId}")
    public ResponseEntity<List<BookCarDto>> getBookedCarsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getBookedCarsByUserId(userId));
    }

    @PostMapping("/car/search")
    public ResponseEntity<CarsDtoList> searchCars(@RequestBody SearchCarDto searchCarDto) {
        return ResponseEntity.ok(customerService.searchCars(searchCarDto));
    }

}
