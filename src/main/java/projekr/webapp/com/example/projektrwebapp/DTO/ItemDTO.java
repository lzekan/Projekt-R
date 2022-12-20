package projekr.webapp.com.example.projektrwebapp.DTO;

import lombok.Getter;

@Getter
public class ItemDTO {
    private String itemType;
    private String model;
    private String location;
    private long amount;
    private String barcode;
}
