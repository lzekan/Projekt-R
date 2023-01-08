package projekr.webapp.com.example.projektrwebapp.Rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import projekr.webapp.com.example.projektrwebapp.DTO.ItemDTO;
import projekr.webapp.com.example.projektrwebapp.DTO.RelocationDTO;
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

    @PostMapping("/api/remove/item")
    private String removeItem(@RequestBody ItemDTO itemDTO) {
        return itemService.removeItem(itemDTO);
    }

    @PostMapping("/api/relocate/item")
    private String relocateItem(@RequestBody RelocationDTO relocationDTO) {
        return itemService.relocateItem(relocationDTO);
    }

}
