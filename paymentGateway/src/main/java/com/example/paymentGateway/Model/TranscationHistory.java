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
public class TranscationHistory {

    int lastbalance;
    int deposit;
    int withdraw;
    int updatedBalance;
    Instant dateAndTime=Instant.now();
}
