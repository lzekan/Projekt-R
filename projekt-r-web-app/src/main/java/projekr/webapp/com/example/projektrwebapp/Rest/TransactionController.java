package projekr.webapp.com.example.projektrwebapp.Rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projekr.webapp.com.example.projektrwebapp.Entity.Transaction;
import projekr.webapp.com.example.projektrwebapp.Service.TransactionService;

@RestController
@AllArgsConstructor
public class TransactionController {

    @Autowired
    private final TransactionService transactionService;

    @GetMapping("/api/add")
    private String addItems(@RequestBody Transaction transaction) {
        return transactionService.addItems(transaction);
    }
}
