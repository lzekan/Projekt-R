package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.Model;

import java.util.ArrayList;

@Repository
public interface ModelRepository extends CrudRepository<Model, Long> {
    @Query(
            value = "select * from model"
    )
    ArrayList<Model> getAllModels();

    @Query(
            value = "select * from model m where model_name = :modelname"
    )
    Model getModelByModelName(@Param("modelname") String modelName);

    @Query(
            value = ("select model.id_model, model.model_name " +
                    "from model join item on model.id_model = item.id_model " +
                    "join item_type on item.id_itemtype = item_type.id_itemtype " +
                    "where item_type.type_name= :itemtypename")
    )
    ArrayList<Model> getAllModelsSameType(@Param("itemtypename") String itemTypeName);
}
