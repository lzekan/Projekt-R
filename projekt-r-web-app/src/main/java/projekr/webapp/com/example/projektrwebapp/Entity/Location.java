package projekr.webapp.com.example.projektrwebapp.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    private long idLocation;
    private String sectorName;

    public Location(String sectorName) {
        this.sectorName = sectorName;
    }
}
