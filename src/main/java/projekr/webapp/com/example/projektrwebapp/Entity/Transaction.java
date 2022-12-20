package projekr.webapp.com.example.projektrwebapp.Entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    private long idTransaction;
    private LocalTime time;
    private long amount;
    private AggregateReference<UserDb, Integer> user;
    private AggregateReference<ActionType, Integer> actionType;
    private AggregateReference<Item, Integer> item;

    public Transaction(LocalTime time, long amount, AggregateReference<UserDb, Integer> user
            , AggregateReference<ActionType, Integer> actionType, AggregateReference<Item, Integer> item) {
        this.time = time;
        this.amount = amount;
        this.user = user;
        this.actionType = actionType;
        this.item = item;
    }
}
