package maniovich.stupidgate.controller;

import maniovich.stupidgate.redis.TransactionRepository;
import maniovich.stupidgate.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GateController {
    Logger logger = LoggerFactory.getLogger(GateController.class);
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    @RequestMapping("areyoualive")
    public String IamAlive(){
        return "I am alive";
    }
    @GetMapping
    @RequestMapping("gate1")
    public String MakeTransaction() {
        String REALM = "gate1";
        String url = "https://functions.yandexcloud.net/d4ecbfpgqphh14daohcn";
        RestTemplateBuilder restTemplate = new RestTemplateBuilder();
        Transaction transaction = new Transaction();
        transactionRepository.Create(transaction);
        transaction.SetTransactionRealm(REALM);
        RequestsController httpHandler = new RequestsController(restTemplate);
        httpHandler.SetUrl(url);

        logger.info("Talking to " + url);


        logger.info("Transaction created with id " + transaction.GetTransactionUUID() +
                " at " + transaction.GetTransactionCreationTimeUTC() );

        logger.info("Transaction realm: " + transaction.GetTransactionRealm());
        logger.info("Transaction state (is Completed): " + transaction.GetTransactionState());

        ResponseEntity<String> response = httpHandler.MakeRequest(transaction.GetTransactionUUID());
        transaction.SetTransactionResponseStatusCode(response.getStatusCodeValue());
        if(transaction.GetTransactionResponseStatusCode() == 200) {
            logger.info("Status code 200, Changing transaction state.");
            transaction.SetTransactionEndTimeUTC();
            transaction.ChangeState();
            transactionRepository.UpdateTransaction(transaction);
            logger.info("Transaction state (is Completed): " + transaction.GetTransactionState());
        }

        logger.info("Done! - " + transaction.GetTransactionUUID() +
                " has been proceed at " + transaction.GetTransactionEndTimeUTC());


        return response.getBody();
    }
}