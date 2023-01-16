package projekr.webapp.com.example.projektrwebapp.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    @Id
    private long idModel;
    private String modelName;

    public Model(String modelName) {
        this.modelName = modelName;
    }
}
