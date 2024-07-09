package com.carrental.services.admin;

import com.carrental.dto.*;
import com.carrental.entity.BookCar;
import com.carrental.entity.Car;
import com.carrental.enums.BookCarStatus;
import com.carrental.repository.BookCarRepository;
import com.carrental.repository.CarRepository;
import com.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CarRepository carRepository;

    private final BookCarRepository bookCarRepository;

    @Override
    public boolean addCar(CarDto carDto) {
        try {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setPrice(carDto.getPrice());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setYear(carDto.getYear());
            car.setTransmission(carDto.getTransmission());
            car.setImg(carDto.getImg().getBytes());
            carRepository.save(car);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public AdminSingleCarDto getCarById(Long carId) {
        AdminSingleCarDto adminSingleCarDto = new AdminSingleCarDto();
        Optional<Car> optionalCar = carRepository.findById(carId);
        optionalCar.ifPresent(car -> adminSingleCarDto.setCarDto(car.getCarDto()));
        return adminSingleCarDto;
    }

//    @Override
//    public boolean updateCar(Long carId, CarDto carDto) throws IOException {
//        Optional<Car> optionalCar = carRepository.findById(carId);
//        if (optionalCar.isPresent()) {
//            Car car = optionalCar.get();
//            if (carDto.getImg() != null) {
//                car.setImg(carDto.getImg().getBytes());
//            }
//            car.getEntity(carDto);
//            carRepository.save(car);
//            return true; // Update was successful
//        } else {
//            return false; // Car not found
//        }
//    }
@Override
public boolean updateCar(Long carId, CarDto carDto) throws IOException {
    Optional<Car> optionalCar = carRepository.findById(carId);
    if (optionalCar.isPresent()) {
        Car existingCar = optionalCar.get();
        existingCar.setName(carDto.getName());
        existingCar.setBrand(carDto.getBrand());
        existingCar.setColor(carDto.getColor());
        existingCar.setPrice(carDto.getPrice());
        existingCar.setType(carDto.getType());
        existingCar.setDescription(carDto.getDescription());
        existingCar.setYear(carDto.getYear());
        existingCar.setTransmission(carDto.getTransmission());
        if (carDto.getImg() != null)
            existingCar.setImg(carDto.getImg().getBytes());
        carRepository.save(existingCar);
        return true;
    }
    return false;
}

    @Override
    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }

    @Override
    public CarsDtoList searchCars(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setColor(searchCarDto.getColor());
        car.setTransmission(searchCarDto.getTransmission());
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAll().withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Car> carExample = Example.of(car, customExampleMatcher);
        List<Car> cars = carRepository.findAll(carExample);
        CarsDtoList carsDtoList = new CarsDtoList();
        carsDtoList.setCarDtoList(cars.stream().map(Car::getCarDto).collect(Collectors.toList()));
        return carsDtoList;
    }

    @Override
    public List<BookCarDto> getBookings() {
        return bookCarRepository.findAll().stream().map(BookCar::getBookCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBookingStatus(Long bookingId, String status) {
        Optional<BookCar> optionalBookCar = bookCarRepository.findById(bookingId);
        if (optionalBookCar.isPresent()) {
            BookCar existingBookCar = optionalBookCar.get();
            if (Objects.equals(status, "Approve")) {
                existingBookCar.setBookCarStatus(BookCarStatus.APPROVED);
            } else {
                existingBookCar.setBookCarStatus(BookCarStatus.REJECTED);
            }
            bookCarRepository.save(existingBookCar);
            return true;
        }
        return false;
    }


}
