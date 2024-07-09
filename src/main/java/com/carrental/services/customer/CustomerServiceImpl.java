package com.carrental.services.customer;

import com.carrental.dto.*;
import com.carrental.entity.BookCar;
import com.carrental.entity.Car;
import com.carrental.entity.User;
import com.carrental.enums.BookCarStatus;
import com.carrental.repository.BookCarRepository;
import com.carrental.repository.CarRepository;
import com.carrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CarRepository carRepository;

    private final UserRepository userRepository;

    private final BookCarRepository bookCarRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public UserSingleCarDto getCarById(Long carId) {
        UserSingleCarDto userSingleCarDto = new UserSingleCarDto();
        Optional<Car> optionalCar = carRepository.findById(carId);
        optionalCar.ifPresent(car -> userSingleCarDto.setCarDto(car.getCarDto()));
        return userSingleCarDto;
    }

    @Override
    public boolean bookACar(Long carId, BookCarDto bookCarDto) {
        User user = null;
        Car car = null;
        Optional<User> userOptional = userRepository.findById(bookCarDto.getUserId());
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (userOptional.isPresent() && optionalCar.isPresent()) {
            user = userOptional.get();
            car = optionalCar.get();
            BookCar bookCar = new BookCar();
            long diffInMilliseconds = bookCarDto.getToDate().getTime() - bookCarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliseconds);
            bookCar.setDays(days);
            bookCar.setToDate(bookCarDto.getToDate());
            bookCar.setFromDate(bookCarDto.getFromDate());
            bookCar.setPrice(optionalCar.get().getPrice() * days);
            bookCar.setBookCarStatus(BookCarStatus.PENDING);
            bookCar.setUser(user);
            bookCar.setCar(car);
            bookCarRepository.save(bookCar);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<BookCarDto> getBookedCarsByUserId(Long userId) {
        return bookCarRepository.findAllByUserId(userId).stream().map(BookCar::getBookCarDto).collect(Collectors.toList());
    }

    @Override
    public CarsDtoList searchCars(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setColor(searchCarDto.getColor());
        car.setTransmission(searchCarDto.getTransmission());
        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAll()
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Car> carExample = Example.of(car, customExampleMatcher);
        List<Car> cars = carRepository.findAll(carExample);
        CarsDtoList carsDtoList = new CarsDtoList();
        carsDtoList.setCarDtoList(cars.stream().map(Car::getCarDto).collect(Collectors.toList()));
        return carsDtoList;
    }


}

