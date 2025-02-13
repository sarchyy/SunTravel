package org.example.Services;

import org.example.Repositories.DestinationRepository;
import org.example.Models.Destination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DestinationServices {

    @Autowired
    private DestinationRepository destinationRepository;

    // Method to retrieve all destinations from the database
    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }


    // Method for saving the file and returning the path
    public String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // Specify the directory where files will be stored
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);

        // Create the directory if it does not exist
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Determine the file path to store the file
        String filePath = uploadDir + file.getOriginalFilename();
        Path destinationPath = uploadPath.resolve(file.getOriginalFilename());

        // Transfer the file to the destination path
        file.transferTo(destinationPath.toFile());

        return filePath;  // Return the file path
    }

    // Method to create and save a new destination in the database
    public Destination createDestination(String name, String description, double price, boolean active, String country, String filePath) {
        // Create a new Destination object
        Destination destination = new Destination(name, description, price, country, active, filePath);

        // Save the destination and return the saved object
        return destinationRepository.save(destination);
    }
}
