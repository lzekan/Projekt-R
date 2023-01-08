package projekr.webapp.com.example.projektrwebapp.Entity;

import lombok.*;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private long idItem;
    private String barcode;
    private long amount;
    private AggregateReference<Model, Long> id_model;
    private AggregateReference<Location, Long> id_location;
    private AggregateReference<ItemType, Long> id_itemtype;

    public Item(String barcode, long amount, AggregateReference<Model, Long> id_model
            , AggregateReference<Location, Long> id_location, AggregateReference<ItemType, Long> id_itemtype) {
        this.barcode = barcode;
        this.amount = amount;
        this.id_model = id_model;
        this.id_location = id_location;
        this.id_itemtype = id_itemtype;
    }
}