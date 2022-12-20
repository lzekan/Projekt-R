package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.Location;

import java.util.ArrayList;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    @Query(
            value = "select * from location"
    )
    ArrayList<Location> getAllLocations();

    @Query(
            value = "select * from location l where l.sector_name = :sectorname"
    )
    Location getLocationBySectorName(@Param("sectorname") String sectorName);
}
