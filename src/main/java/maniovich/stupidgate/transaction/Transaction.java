package maniovich.stupidgate.transaction;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("TRANSACTION")
public class Transaction implements Serializable {
    private String transactionUUID;
    private boolean transactionState;

    public Transaction(){
        transactionUUID = UUID.randomUUID().toString();
        transactionState = false;
    }


    public String GetTransactionUUID(){
        return transactionUUID;
    }
    public boolean GetTransactionState(){
        return transactionState;
    }
    public void ChangeState(boolean transactionState) {
        this.transactionState = transactionState;
    }
}
