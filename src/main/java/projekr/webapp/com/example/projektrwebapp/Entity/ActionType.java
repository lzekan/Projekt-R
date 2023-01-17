package projekr.webapp.com.example.projektrwebapp.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionType {
    @Id
    private long idAction;
    private String name;

    public ActionType(String name) {
        this.name = name;
    }
}
