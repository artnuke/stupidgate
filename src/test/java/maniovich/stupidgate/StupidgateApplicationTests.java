package maniovich.stupidgate;

import maniovich.stupidgate.controller.GateController;
import maniovich.stupidgate.transaction.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class StupidgateApplicationTests {

    @Autowired
    GateController gateController;


    //Transaction test = new Transaction();

    @Test
    void contextLoads() {
///
    }

}
