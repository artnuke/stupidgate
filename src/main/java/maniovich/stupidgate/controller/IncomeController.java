package maniovich.stupidgate.controller;

import maniovich.stupidgate.redis.TransactionRepository;
import maniovich.stupidgate.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IncomeController{
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
        transactionRepository.Create(transaction);
        if(httpHandler.MakeRequest(transaction.GetTransactionUUID()).getStatusCodeValue() == 200){
            System.out.println("Transaction state " + transactionRepository.GetTransactionState
                            (transaction.GetTransactionUUID())
                    .GetTransactionState());
            transaction.ChangeState(true);
            transactionRepository.ChangeTransactionState(transaction);
        }
        System.out.println("StatusCode = " + httpHandler.MakeRequest(transaction.GetTransactionUUID()).getStatusCode());
        System.out.println("Body: " + httpHandler.MakeRequest(transaction.GetTransactionUUID()).getBody());
        System.out.println("Transaction state " + transactionRepository.GetTransactionState
                (transaction.GetTransactionUUID()).GetTransactionState());
        return httpHandler.MakeRequest(transaction.GetTransactionUUID()).getBody();
    }
}