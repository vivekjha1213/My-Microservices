package com.example.paymentGateway.Repo;

import com.example.paymentGateway.Model.TranscationHistory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionsRepo {

    void sendMoney(String senderMobileNo, String recieverMobileNo, int amountToSend);

    ResponseEntity<Object> addMoneyToWallect(String mobileNo, int amountToAdd);

    List<TranscationHistory> getAllTranscation(String mobileNo);
}
