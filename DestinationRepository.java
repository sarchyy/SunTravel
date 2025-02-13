package org.example.Repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.Models.Destination;
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {}



/*import org.springframework.stereotype.Repository;
import org.example.suntravel.Models.Destination;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
@Repository

public class DestinationRepository {
    private List<Destination> destinations = new ArrayList<>();

    public List<Destination> findAll() {
        return destinations;
    }

    public Optional<Destination> findByNameAndCountry(String name, String country) {
        return destinations.stream()
                .filter(destination -> destination.getName().equals(name) && destination.getCountry().equals(country))
                .findFirst();
    }

    public Destination save(Destination destination) {

        if (findByNameAndCountry(destination.getName(), destination.getCountry()).isEmpty()) {
            destinations.add(destination);
        }
        return destination;
    }

    public void deleteByNameAndCountry(String name, String country) {
        destinations.removeIf(destination -> destination.getName().equals(name) && destination.getCountry().equals(country));
    }

    public void update(Destination updatedDestination) {
        findByNameAndCountry(updatedDestination.getName(), updatedDestination.getCountry()).ifPresent(existingDestination -> {
            existingDestination.setDescription(updatedDestination.getDescription());
        });
    }
}*/
