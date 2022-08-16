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
import org.springframework.web.bind.annotation.RequestParam;
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
    public String MakeTransaction(@RequestParam String stupidSample){

        String realm = "gate1";
        String url = "https://functions.yandexcloud.net/d4ecbfpgqphh14daohcn";

        Transaction transaction = new Transaction(realm, stupidSample);
        transactionRepository.Create(transaction);

        RestTemplateBuilder restTemplate = new RestTemplateBuilder();
        RequestsController httpHandler = new RequestsController(restTemplate);
        httpHandler.SetUrl(url);

        logger.info("Talking to " + url);

        logger.info("Transaction created with id " + transaction.GetTransactionUUID() +
                " at " + transaction.GetTransactionCreationTimeUTC());

        logger.info("Transaction realm: " + transaction.GetTransactionRealm());
        logger.info("Transaction state (is Completed): " + transaction.GetTransactionState());

        ResponseEntity<String> response = httpHandler.MakeRequest(transaction.GetTransactionUUID(),
                transaction.GetTransactionQuestion());
        transaction.SetTransactionStatusCode(response.getStatusCodeValue());

        if(transaction.GetTransactionResponseStatusCode() == 200) {
            logger.info("Status code 200, Changing transaction state.");
            transaction.SetAnswer(response.getBody());
            transaction.SetTransactionEndTimeUTC();
            transaction.ChangeState();
            transactionRepository.UpdateTransaction(transaction);
            logger.info("Transaction state (is Completed): " + transaction.GetTransactionState());
        }

        logger.info("Done! - " + transaction.GetTransactionUUID() +
                " has been processed at " + transaction.GetTransactionEndTimeUTC());
        logger.info("JSON: " + transaction.ToJSONObject().toString());

        return transaction.ToJSONObject().toString(2);
    }
    @GetMapping("gate2")
    public String gate2(@RequestParam String sample){
        return sample;
    }

}