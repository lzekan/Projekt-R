package projekr.webapp.com.example.projektrwebapp.Rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projekr.webapp.com.example.projektrwebapp.DTO.UserDbDTO;
import projekr.webapp.com.example.projektrwebapp.Entity.UserDb;
import projekr.webapp.com.example.projektrwebapp.Service.UserDbService;

@RestController
@AllArgsConstructor
public class UserDbController {
    @Autowired
    private final UserDbService userDbService;

    @GetMapping("/api/user")
    private UserDb findUser(@RequestParam("idUser") long idUser) {
        return userDbService.findUser(idUser);
    }

    @PostMapping("/api/user/add")
    private String addNewUser(@RequestBody UserDbDTO userDbDTO) {
        return userDbService.addNewUser(userDbDTO);
    }


}
