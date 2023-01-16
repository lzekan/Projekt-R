package projekr.webapp.com.example.projektrwebapp.Repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projekr.webapp.com.example.projektrwebapp.Entity.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    @Query(
        value ="insert into transaction values (:transaction.time, :transaction.location)"
    )
    String addItemsInDb(@Param("transaction") Transaction transaction);
}
