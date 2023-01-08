package projekr.webapp.com.example.projektrwebapp.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemType {
    @Id
    private long idItemtype;
    private String typeName;

    public ItemType(String typeName) {
        this.typeName = typeName;
    }
}
