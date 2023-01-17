package projekr.webapp.com.example.projektrwebapp.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RelocationDTO {
    private String itemType;
    private String model;
    private String locationFrom;
    private String locationTo;
    private long amount;
    private String barcode;


}
