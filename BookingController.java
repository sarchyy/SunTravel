package org.example.Controllers;

import org.example.Models.Booking;
import org.example.Repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;


    @Autowired
    public BookingController(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @GetMapping
    public String listBookings(Model model) {

        List<Booking> bookings = bookingRepository.findAll();
        model.addAttribute("bookings", bookings);
        return "bookingList";
    }

    @GetMapping("/add")
    public String addBookingForm(Model model) {
        model.addAttribute("booking", new Booking());
        return "bookingAdd";
    }

    @PostMapping("/add")
    public String addBooking(@ModelAttribute Booking booking) {

        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/{bookingId}")
    public String viewBooking(@PathVariable Long bookingId, Model model) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            model.addAttribute("booking", booking.get());
            return "bookingView";
        } else {
            model.addAttribute("error", "Booking not found!");
            return "error";
        }
    }

    @GetMapping("/edit/{bookingId}")
    public String editBooking(@PathVariable Long bookingId, Model model) {
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if (booking.isPresent()) {
            model.addAttribute("booking", booking.get());
            return "bookingEdit";
        } else {
            model.addAttribute("error", "Booking not found!");
            return "error";
        }
    }

    @PostMapping("/edit/{bookingId}")
    public String saveEditedBooking(@PathVariable Long bookingId, @ModelAttribute Booking booking) {

        booking.setId(bookingId);
        bookingRepository.save(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/delete/{bookingId}")
    public String deleteBooking(@PathVariable Long bookingId) {

        bookingRepository.deleteById(bookingId);
        return "redirect:/bookings";
    }
}
