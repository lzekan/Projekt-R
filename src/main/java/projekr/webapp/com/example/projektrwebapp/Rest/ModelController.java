package projekr.webapp.com.example.projektrwebapp.Rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import projekr.webapp.com.example.projektrwebapp.Entity.Model;
import projekr.webapp.com.example.projektrwebapp.Service.ModelService;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class ModelController {
    @Autowired
    private final ModelService modelService;

    @GetMapping("/api/get/all/model")
    private ArrayList<Model> getAllModels() {
        return modelService.getAllModels();
    }
    @GetMapping("/api/get/all/model/sametype")
    private ArrayList<Model> getAllModelsSameType(@RequestParam("itemTypeName") String itemTypeName) {
        return modelService.getAllModelsSameType(itemTypeName);
    }
}
