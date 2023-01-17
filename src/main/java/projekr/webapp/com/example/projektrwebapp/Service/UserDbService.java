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

    public UserDb findUserByUsername(String username) {
        return userDbRepository.findUserDbByUsername(username);
    }

    public UserDb findUserByEmail(String email){
        return userDbRepository.findUserDbByEmail(email);
    }

    public String addNewUser(UserDbDTO newUser) {
        UserDb potentialUser = null;

        potentialUser = findUserByEmail(newUser.getEmail());
        if(potentialUser != null) {
            return "Korisnik s unesenim emailom vec postoji.";
        }

        potentialUser = findUserByUsername(newUser.getUsername());
        if(potentialUser != null) {
            return "Korisnicko ime se vec koristi.";
        }

        //String encodedPassword = bCryptPasswordEncoder
         //       .encode(newUser.getPassword());


        UserDb createNewUser = UserDb.builder()
                .username(newUser.getUsername())
                .password(newUser.getPassword())
                .email(newUser.getEmail())
                .build();


        userDbRepository.save(createNewUser);
        return "Korisnik uspjesno dodan.";
    }
}
