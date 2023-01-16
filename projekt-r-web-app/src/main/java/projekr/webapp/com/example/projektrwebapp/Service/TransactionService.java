package projekr.webapp.com.example.projektrwebapp.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import projekr.webapp.com.example.projektrwebapp.Entity.Transaction;
import projekr.webapp.com.example.projektrwebapp.Repository.TransactionRepository;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public String addItems(Transaction transaction){
        return transactionRepository.addItemsInDb(transaction);
    }
}
