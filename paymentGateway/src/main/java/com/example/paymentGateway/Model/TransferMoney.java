package com.example.paymentGateway.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoney {

    String senderMobileNo;
    String reciverMobileNo;
    int amountToSend;
    Instant date= Instant.now();
}