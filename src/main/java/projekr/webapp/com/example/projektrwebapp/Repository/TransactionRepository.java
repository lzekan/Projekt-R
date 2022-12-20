package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
