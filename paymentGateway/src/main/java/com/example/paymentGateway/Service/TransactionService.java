package com.example.paymentGateway.Service;

import com.example.paymentGateway.Model.TransferMoney;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TransactionService {

    ResponseEntity<Object> transferMoneyToOthersWallet(TransferMoney transferMoney);

    ResponseEntity<Object> addMoneyToWallet(Map<String, Object> addMoneyToWallet);

    ResponseEntity<Object> viewAllTranscation(String mobileNo);
}
