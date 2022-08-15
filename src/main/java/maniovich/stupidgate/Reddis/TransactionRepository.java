package maniovich.stupidgate.Reddis;

import maniovich.stupidgate.transaction.Transaction;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TransactionRepository{

    private HashOperations hashOperations;

    public TransactionRepository(RedisTemplate redisTemplate){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void Create(Transaction transaction){
        hashOperations.put("TRANSACTION", transaction.GetTransactionUUID(), transaction);
        //logger.info(String.format("User with ID %s saved", user.getUserId()));
    }
    public Transaction GetTransactionState(String id){
        return (Transaction) hashOperations.get("TRANSACTION", id);
    }

    public void ChangeTransactionState(Transaction transaction){
        hashOperations.put("TRANSACTION", transaction.GetTransactionUUID(), transaction);
    }

    public Map<String, Transaction> GetAll(String id){
        return hashOperations.entries("TRANSACTION");
    }

}