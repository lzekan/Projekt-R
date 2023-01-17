package projekr.webapp.com.example.projektrwebapp.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemDTO {
    private String itemType;
    private String model;
    private String location;
    private long amount;
    private String barcode;

    public ItemDTO(String itemType, String model, String locationFrom, long amount, String barcode){
        this.itemType = itemType;
        this.model = model;
        this.location = locationFrom;
        this.amount = amount;
        this.barcode = barcode;
    }
}
