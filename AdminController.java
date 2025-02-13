package org.example.Controllers;


import org.example.Models.Destination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")

public class AdminController {


    @GetMapping("/destinations")
    public String manageDestinations() {
        return "manageDestinations";
    }

    @PostMapping("/destinations/create")
    public String createDestination(@ModelAttribute Destination destination) {
        return "redirect:/admin/destinations";
    }

    @PostMapping("/destinations/delete/{id}")
    public String deleteDestination(@PathVariable Long id) {
        return "redirect:/admin/destinations";
    }
}

