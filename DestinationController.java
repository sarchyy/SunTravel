package org.example.Controllers;

import org.example.Models.Destination;
import org.example.Repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationRepository destinationRepository;

    @Value("${file.upload-dir:src/main/resources/static/Images}")
    private String uploadDir;

    @Autowired
    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/details/{id}")
    public String getDestinationDetails(@PathVariable("id") Long id, Model model) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination not found"));
        model.addAttribute("destination", destination);
        return "destinationDetail"; 
    }

    @GetMapping
    public String showDestinations(Model model) {
        List<Destination> destinations = destinationRepository.findAll();
        model.addAttribute("destinations", destinations);
        return "destinations";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("destination", new Destination());
        return "createDestination";
    }

    @PostMapping("/create")
    public String createDestination(
            @Validated @ModelAttribute Destination destination,
            BindingResult result,
            @RequestParam("file") MultipartFile file,
            Model model) {

        if (result.hasErrors()) {
            return "createDestination";
        }


        if (!file.isEmpty()) {
            try {

                String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();


                String filePath = saveFile(file, uniqueFileName);
                destination.setFilePath(filePath);
            } catch (IOException e) {
                model.addAttribute("error", "File upload failed: " + e.getMessage());
                return "createDestination";
            }
        }


        destinationRepository.save(destination);
        return "redirect:/destinations";
    }

    private String saveFile(MultipartFile file, String uniqueFileName) throws IOException {

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }


        File uploadFile = new File(dir, uniqueFileName);


        file.transferTo(uploadFile);


        return "/Images/" + uniqueFileName;
    }

    @PostMapping("/delete/{id}")
    public String deleteDestination(@PathVariable Long id) {
        destinationRepository.deleteById(id);
        return "redirect:/destinations";
    }
}
