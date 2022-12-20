package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.Item;

import java.util.ArrayList;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query(
            value = "select * from item"
    )
    ArrayList<Item> getAllItems();

    @Modifying
    @Query(
            value = "update item set amount=amount+ :amount where id_item= :iditem"
    )
    void updateItem(@Param("iditem") long idItem, @Param("amount") long amount);
}
