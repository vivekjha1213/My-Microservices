package com.example.paymentGateway.Controller;

import com.example.paymentGateway.Model.TransferMoney;
import com.example.paymentGateway.Service.TransactionService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transcations")
public class TrascationController {

    Logger logger=LoggerFactory.getLogger(TrascationController.class);

    @Autowired
    TransactionService transactionService;

    @PostMapping("/api/transferMoneyToOthersWallet")
    public ResponseEntity<Object> signUpForNewUsers(@RequestBody TransferMoney transferMoney){

        try{
            long t1=System.currentTimeMillis();
            if(transferMoney == null){
                return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> responseEntity = transactionService.transferMoneyToOthersWallet(transferMoney);
            logger.info("controller timing : {} : {}", (System.currentTimeMillis() - t1), "transferMoneyToOthersWallet");
            return responseEntity;

        }catch (Exception exception){
            exception.printStackTrace();
            String error = exception.getMessage();
            logger.error("Error in API- /transferMoneyToOthersWallet"  + error);
            return new ResponseEntity(new Response(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping("/api/addMoneyToWallet")
    public ResponseEntity<Object> signUpForNewUsers(@RequestBody Map<String,Object> addMoneyToWallet){

        try{
            long t1=System.currentTimeMillis();
            if(addMoneyToWallet == null){
                return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> responseEntity = transactionService.addMoneyToWallet(addMoneyToWallet);
            logger.info("controller timing : {} : {}", (System.currentTimeMillis() - t1), "addMoneyToWallet");
            return responseEntity;

        }catch (Exception exception){
            exception.printStackTrace();
            String error = exception.getMessage();
            logger.error("Error in API- /addMoneyToWallet"  + error);
            return new ResponseEntity("Error in API-addMoneyToWallet",HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/api/viewPassbook")
    public ResponseEntity<Object> signUpForNewUsers(@RequestParam String mobileNo){

        try{
            long t1=System.currentTimeMillis();
            if(mobileNo == null){
                return new ResponseEntity<>("Invalid Input", HttpStatus.BAD_REQUEST);
            }
            ResponseEntity<Object> responseEntity = transactionService.viewAllTranscation(mobileNo);
            logger.info("controller timing : {} : {}", (System.currentTimeMillis() - t1), "viewPassbook");
            return responseEntity;

        }catch (Exception exception){
            exception.printStackTrace();
            String error = exception.getMessage();
            logger.error("Error in API- /viewPassbook"  + error);
            return new ResponseEntity("Error in API-viewPassbook",HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


}
