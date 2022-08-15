package maniovich.stupidgate.controller;

import maniovich.stupidgate.redis.TransactionRepository;
import maniovich.stupidgate.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncomeController{
    Logger logger = LoggerFactory.getLogger(IncomeController.class);
    @Autowired
    private TransactionRepository transactionRepository;
    @GetMapping
    @RequestMapping("gate")
    public String MakeTransaction() {
        String url = "https://functions.yandexcloud.net/d4ecbfpgqphh14daohcn";
        RestTemplateBuilder restTemplate = new RestTemplateBuilder();
        Transaction transaction = new Transaction();
        RequestsController httpHandler = new RequestsController(restTemplate);
        httpHandler.SetUrl(url);
        logger.info("Talking to " + url);

        transactionRepository.Create(transaction);
        logger.info("Transaction created with id " + transaction.GetTransactionUUID());
        logger.info("Transaction state (is Completed): " + transaction.GetTransactionState());

        if(httpHandler.MakeRequest(transaction.GetTransactionUUID()).getStatusCodeValue() == 200){
            logger.info("Status code 200, Changing transaction state");
            transaction.ChangeState();
            transactionRepository.ChangeTransactionState(transaction);
            logger.info("Transaction state (is Completed): " + transaction.GetTransactionState());

        }

        logger.info("Done!: " + transaction.GetTransactionUUID() + " has been proceed");
        return httpHandler.MakeRequest(transaction.GetTransactionUUID()).getBody();
    }
}