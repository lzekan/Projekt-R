package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.ItemType;

import java.util.ArrayList;

@Repository
public interface ItemTypeRepository extends CrudRepository<ItemType, Integer> {

    @Query(
            value = "select * from item_type"
    )
    ArrayList<ItemType> getAllItemTypes();

   @Query(
           value = "select * from item_type it where it.type_name = :typename"
   )
    ItemType getItemTypeByTypeName(@Param("typename") String typeName);

    @Query(
            value = "select * from item_type it where it.id_itemtype = :iditemtype"
    )
    ItemType getItemTypeByIdItemtype(@Param("iditemtype") long idItemType);
}
