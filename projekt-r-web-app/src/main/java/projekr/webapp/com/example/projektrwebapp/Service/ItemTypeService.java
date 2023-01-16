package projekr.webapp.com.example.projektrwebapp.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import projekr.webapp.com.example.projektrwebapp.Entity.ItemType;
import projekr.webapp.com.example.projektrwebapp.Repository.ItemTypeRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ItemTypeService {

    private final ItemTypeRepository itemTypeRepository;

    @GetMapping("/api/get/all/itemtype")
    public ArrayList<ItemType> getAllItemTypes() {
        return itemTypeRepository.getAllItemTypes();
    }
}
