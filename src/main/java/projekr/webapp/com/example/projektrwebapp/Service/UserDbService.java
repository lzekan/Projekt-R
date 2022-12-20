package projekr.webapp.com.example.projektrwebapp.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projekr.webapp.com.example.projektrwebapp.DTO.UserDbDTO;
import projekr.webapp.com.example.projektrwebapp.Entity.UserDb;
import projekr.webapp.com.example.projektrwebapp.Repository.UserDbRepository;

@Service
@AllArgsConstructor
public class UserDbService {
    private final UserDbRepository userDbRepository;

    public UserDb findUser(long idUser) {
        return userDbRepository.findUserDbById(idUser);
    }

    public String addNewUser(UserDbDTO newUser) {
        UserDb createNewUser = UserDb.builder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .build();
        userDbRepository.save(createNewUser);
        return "uspjesno dodano";
    }
}
