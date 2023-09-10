package com.example.paymentGateway.Service;

import com.example.paymentGateway.Model.TranscationHistory;
import com.example.paymentGateway.Model.TransferMoney;
import com.example.paymentGateway.Repo.TransactionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TranscationServiceImpl implements TransactionService {

    @Autowired
    TransactionsRepo transactionsRepo;

    @Override
    public ResponseEntity<Object> transferMoneyToOthersWallet(TransferMoney transferMoney) {

        String senderMobileNo=transferMoney.getSenderMobileNo();
        String recieverMobileNo=transferMoney.getReciverMobileNo();
        int amountToSend=transferMoney.getAmountToSend();
        transactionsRepo.sendMoney(senderMobileNo,recieverMobileNo,amountToSend);

        return new ResponseEntity<>("Money Transfered", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> addMoneyToWallet(Map<String, Object> addMoneyToWallet) {

        String mobileNo= (String) addMoneyToWallet.get("mobileNo");
        int amountToAdd= (int) addMoneyToWallet.get("amountToAdd");
        ResponseEntity<Object> details;
        if((mobileNo!=null && mobileNo.length()==10 )&&amountToAdd>0){
         details=  transactionsRepo.addMoneyToWallect(mobileNo,amountToAdd);
         return details;
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> viewAllTranscation(String mobileNo) {

        if(!(mobileNo.length() ==10)){
            return new ResponseEntity<>("Not a Valid Mobile No", HttpStatus.BAD_REQUEST);
        }
        List<TranscationHistory> transcation_history=transactionsRepo.getAllTranscation(mobileNo);
        return new ResponseEntity<>("The trascation history of This account "+transcation_history,HttpStatus.OK);

    }
}
