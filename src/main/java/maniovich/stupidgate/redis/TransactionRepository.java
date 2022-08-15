package maniovich.stupidgate.redis;

import maniovich.stupidgate.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TransactionRepository{
    Logger logger = LoggerFactory.getLogger(TransactionRepository.class);
    private HashOperations hashOperations;

    public TransactionRepository(RedisTemplate redisTemplate){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void Create(Transaction transaction){
        hashOperations.put("TRANSACTION", transaction.GetTransactionUUID(), transaction);
        logger.info(String.format("Transaction with ID %s saved into Redis DB", transaction.GetTransactionUUID()));
    }
    public Transaction GetTransactionState(String id){
        return (Transaction) hashOperations.get("TRANSACTION", id);

    }

    public void UpdateTransaction(Transaction transaction){
        hashOperations.put("TRANSACTION", transaction.GetTransactionUUID(), transaction);
        logger.info(String.format("Transaction with ID %s updated", transaction.GetTransactionUUID()));
    }

    public Map<String, Transaction> GetAll(String id){
        return hashOperations.entries("TRANSACTION");
    }

}