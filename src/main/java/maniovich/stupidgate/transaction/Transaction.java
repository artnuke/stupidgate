package maniovich.stupidgate.transaction;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
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
    private String transactionQuestion;
    private String transactionAnswer;
    private int transactionResponseStatusCode;

    public Transaction(String realm, String transactionQuestion){
        transactionUUID = UUID.randomUUID().toString();
        transactionState = false;
        this.transactionRealm = realm;
        transactionCreationTimeUTC = Instant.now().toString();
        this.transactionQuestion = transactionQuestion;
        this.transactionAnswer = "nothing";

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
    public String GetTransactionQuestion(){
        return transactionQuestion;
    }
    public String GetTransactionAnswer(){
        return transactionAnswer;
    }

    public int GetTransactionResponseStatusCode(){
        return transactionResponseStatusCode;
    }


    //SETTERS
    public void ChangeState() {
        this.transactionState = !transactionState;
    }

    public void SetTransactionEndTimeUTC(){
        this.transactionEndTimeUTC = Instant.now().toString();
    }
    public void SetTransactionStupidData(String SetTransactionStupidData){
        this.transactionQuestion = SetTransactionStupidData;
    }
    public void SetTransactionStatusCode(int transactionResponseStatusCode) {
        this.transactionResponseStatusCode = transactionResponseStatusCode;
    }

    public void SetAnswer(String transactionStupidData_after){
        this.transactionAnswer = transactionStupidData_after;
    }

    //METHODS
    public JSONObject ToJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.transactionUUID);
        jsonObject.put("state", this.transactionState);
        jsonObject.put("realm", this.transactionRealm);
        jsonObject.put("creationTime", this.transactionCreationTimeUTC);
        jsonObject.put("endTime", this.transactionEndTimeUTC);
        jsonObject.put("responseStatusCode", this.transactionResponseStatusCode);
        jsonObject.put("data_question", this.transactionQuestion);
        jsonObject.put("data_answer", this.transactionAnswer);
        return jsonObject;
    }
}
