package projekr.webapp.com.example.projektrwebapp.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projekr.webapp.com.example.projektrwebapp.Entity.Location;
import projekr.webapp.com.example.projektrwebapp.Repository.LocationRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public ArrayList<Location> getAllLocations() {
        return  locationRepository.getAllLocations();
    }
}
