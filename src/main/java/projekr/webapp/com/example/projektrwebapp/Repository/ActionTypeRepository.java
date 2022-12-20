package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.ActionType;

@Repository
public interface ActionTypeRepository extends CrudRepository<ActionType, Long> {
}
