package com.example.paymentGateway.Model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "AccountDetails")
public class AccountDetails {

    String mobileNo;
    String accountNo;
    String userId;
    String userName;
    int accountBalance;
    List<TranscationHistory> transcation_history;
}
