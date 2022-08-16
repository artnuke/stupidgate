package maniovich.stupidgate.transaction;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@RedisHash("TRANSACTION")
public class Transaction implements Serializable {
    private String transactionUUID;
    private boolean transactionState;
    private String transactionRealm;
    private String transactionCreationTimeUTC;
    private String transactionEndTimeUTC;
    private String transactionStupidData;

    private int transactionResponseStatusCode;

    public Transaction(){
        transactionUUID = UUID.randomUUID().toString();
        transactionState = false;
        transactionCreationTimeUTC = Instant.now().toString();

    }
    //GETTERS
    public String GetTransactionUUID(){
        return transactionUUID;
    }
    public boolean GetTransactionState(){
        return transactionState;
    }
    public String GetTransactionCreationTimeUTC(){
        return transactionCreationTimeUTC;
    }
    public String GetTransactionEndTimeUTC(){
        return transactionEndTimeUTC;
    }
    public String GetTransactionRealm(){
        return transactionRealm;
    }
    public String GetTransactionStupidData(){
        return transactionStupidData;
    }

    public int GetTransactionResponseStatusCode(){
        return transactionResponseStatusCode;
    }


    //SETTERS
    public void ChangeState() {
        this.transactionState = !transactionState;
    }
    public void SetTransactionRealm(String transactionRealm){
        this.transactionRealm = transactionRealm;
    }
    public void SetTransactionEndTimeUTC(){
        this.transactionEndTimeUTC = Instant.now().toString();
    }

    public void SetTransactionStupidData(String SetTransactionStupidData){
        this.transactionStupidData = SetTransactionStupidData;
    }

    public void SetTransactionResponseStatusCode(int transactionResponseStatusCode) {
        this.transactionResponseStatusCode = transactionResponseStatusCode;
    }
}
