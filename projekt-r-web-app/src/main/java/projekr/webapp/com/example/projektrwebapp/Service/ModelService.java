package projekr.webapp.com.example.projektrwebapp.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projekr.webapp.com.example.projektrwebapp.Entity.Model;
import projekr.webapp.com.example.projektrwebapp.Repository.ModelRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class ModelService {
    private final ModelRepository modelRepository;

    public ArrayList<Model> getAllModels() {
        return modelRepository.getAllModels();
    }

    public ArrayList<Model> getAllModelsSameType(String itemTypeName) {
        return modelRepository.getAllModelsSameType(itemTypeName);
    }


}
