package projekr.webapp.com.example.projektrwebapp.Rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projekr.webapp.com.example.projektrwebapp.Entity.ItemType;
import projekr.webapp.com.example.projektrwebapp.Service.ItemTypeService;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class ItemTypeController {

    @Autowired
    private final ItemTypeService itemTypeService;

    @GetMapping("/api/get/all/itemtype")
    private ArrayList<ItemType> getAllItemTypes() {
        return itemTypeService.getAllItemTypes();
    }

    @GetMapping("/")
    private void getX(){
        System.out.println("bobo");
    }
}
