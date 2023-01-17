package projekr.webapp.com.example.projektrwebapp.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projekr.webapp.com.example.projektrwebapp.Entity.Model;
import projekr.webapp.com.example.projektrwebapp.Repository.ModelRepository;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class ModelService {
    private final ModelRepository modelRepository;

    public ArrayList<Model> getAllModels() {
        return modelRepository.getAllModels();
    }

    public ArrayList<Model> getAllModelsSameType(String itemTypeName) {
        ArrayList<Model> returnedList = modelRepository.getAllModelsSameType(itemTypeName);
        ArrayList<Model> noDuplicates = new ArrayList<>();
        ArrayList<Long> idList = new ArrayList<>();

        for (Model model : returnedList) {
            if (!idList.contains(model.getIdModel())) {
                noDuplicates.add(model);
                idList.add(model.getIdModel());
            }


        }

        return noDuplicates;
    }

}
