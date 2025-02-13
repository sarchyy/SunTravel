package org.example.Repositories;


import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.Models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

}

/*
import org.example.suntravel.Controllers.BookingController;
import org.example.suntravel.Models.Booking;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookingRepository {
    private List<Booking> bookings = new ArrayList<>();
    private Long currentId = 1L;


    public List<Booking> findAll() {
        return bookings;
    }


    public Optional<Booking> findById(Long id) {
        return bookings.stream().filter(booking -> booking.getId().equals(id)).findFirst();
    }

    public Booking save(Booking booking) {
        booking.setId(currentId++);
        bookings.add(booking);
        return booking;
    }


    public void deleteById(Long id) {
        bookings.removeIf(booking -> booking.getId().equals(id));
    }

    public void update(Booking updatedBooking) {
        bookings.stream()
                .filter(booking -> booking.getId().equals(updatedBooking.getId()))
                .findFirst()
                .ifPresent(existingBooking -> {
                    existingBooking.setUserId(updatedBooking.getUserId());
                    existingBooking.setDestinationId(updatedBooking.getDestinationId());
                    existingBooking.setDate(updatedBooking.getDate());
                });
    }
}*/