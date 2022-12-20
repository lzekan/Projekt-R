package projekr.webapp.com.example.projektrwebapp.Rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projekr.webapp.com.example.projektrwebapp.Entity.Location;
import projekr.webapp.com.example.projektrwebapp.Service.LocationService;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class LocationController {
    @Autowired
    private final LocationService locationService;

    @GetMapping("/api/get/all/location")
    private ArrayList<Location> getAllLocations() {
        return locationService.getAllLocations();
    }
}
