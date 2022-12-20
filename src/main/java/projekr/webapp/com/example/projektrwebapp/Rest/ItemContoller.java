package projekr.webapp.com.example.projektrwebapp.Rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projekr.webapp.com.example.projektrwebapp.DTO.ItemDTO;
import projekr.webapp.com.example.projektrwebapp.Entity.Item;
import projekr.webapp.com.example.projektrwebapp.Service.ItemService;

@RestController
@AllArgsConstructor
public class ItemContoller {
    @Autowired
    private final ItemService itemService;

    @PostMapping("/api/add/item")
    private String addItem(@RequestBody ItemDTO itemDTO) {
        return itemService.addItem(itemDTO);
    }

}
