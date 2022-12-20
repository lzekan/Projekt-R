package projekr.webapp.com.example.projektrwebapp.Entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDb {
    @Id
    private long idUser;
    private String username;
    private String password;
    private String email;

    public UserDb(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
