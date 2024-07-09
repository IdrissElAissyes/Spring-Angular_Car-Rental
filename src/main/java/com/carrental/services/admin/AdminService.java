package com.carrental.services.admin;

import com.carrental.dto.*;
import com.carrental.entity.Car;

import java.io.IOException;
import java.util.List;

public interface AdminService {

boolean addCar(CarDto carDto);
//    boolean AddCar(CarDto carDto);

    List<CarDto> getAllCars();

    AdminSingleCarDto getCarById(Long carId);
    boolean updateCar(Long carId,CarDto carDto) throws IOException;
//    boolean updateCar(Long carId, CarDto carDto) throws IOException;

    void deleteCar(Long carId);

    CarsDtoList searchCars(SearchCarDto searchCarDto);

    List<BookCarDto> getBookings();

    boolean changeBookingStatus(Long bookingId, String status);
}
