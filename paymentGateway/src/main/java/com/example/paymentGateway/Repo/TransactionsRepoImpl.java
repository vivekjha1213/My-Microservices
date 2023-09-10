package com.example.paymentGateway.Repo;

import com.example.paymentGateway.Model.AccountDetails;
import com.example.paymentGateway.Model.TranscationHistory;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TransactionsRepoImpl implements  TransactionsRepo{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    AppRepoImpl appRepo;

    public void sendMoney(String senderMobileNo, String recieverMobileNo, int amountToSend) {

        boolean checkIfSenderMobileNoExistsInDb=appRepo.checkIfThisMobileNoExists(senderMobileNo);
        boolean checkReciverMobileNoExistsInDb=appRepo.checkIfThisMobileNoExists(recieverMobileNo);

        if(checkIfSenderMobileNoExistsInDb && checkReciverMobileNoExistsInDb ){

            Criteria senderDetails=Criteria.where("mobileNo").is(senderMobileNo).and("accountBalance").gte(amountToSend);
            Query senderQuery=new Query();
            senderQuery.addCriteria(senderDetails);
            AccountDetails senderDetail= mongoTemplate.findOne(senderQuery,AccountDetails.class);


            Criteria recieverDetails=Criteria.where("mobileNo").is(recieverMobileNo);
            Query reciverQuery=new Query();
            reciverQuery.addCriteria(recieverDetails);
            AccountDetails reciverDetails=mongoTemplate.findOne(reciverQuery,AccountDetails.class);


            if(senderDetail!=null && reciverDetails!=null){
                performOperationOfSendingMoney(senderDetail,reciverDetails,amountToSend);

            }
        }
    }

    private void performOperationOfSendingMoney(AccountDetails senderDetail, AccountDetails reciverDetails,int amountToSend) {

        //Sender
        int accountBalanceOfSender=senderDetail.getAccountBalance();
        int accountbalanceAfterDedeuction=accountBalanceOfSender-amountToSend;

        Update updateForSender=new Update();
        updateForSender.set("accountBalance",accountbalanceAfterDedeuction);
        TranscationHistory transcationHistoryForSender=new TranscationHistory();
        transcationHistoryForSender.setWithdraw(amountToSend);
        transcationHistoryForSender.setLastbalance(accountBalanceOfSender);
        transcationHistoryForSender.setUpdatedBalance(accountbalanceAfterDedeuction);
        updateForSender.addToSet("transcation_history",transcationHistoryForSender);

        Query queryforSender=new Query();
        queryforSender.addCriteria(Criteria.where("mobileNo").is(senderDetail.getMobileNo()));

        //Reciever
        int accountBalanceOfReciver=reciverDetails.getAccountBalance();
        int accountBalanceAfterAdding=accountBalanceOfReciver+amountToSend;

        Update updateForReciver=new Update();
        updateForReciver.set("accountBalance",accountBalanceAfterAdding);
        TranscationHistory transcationHistoryForReciever=new TranscationHistory();
        transcationHistoryForReciever.setLastbalance(reciverDetails.getAccountBalance());
        transcationHistoryForReciever.setDeposit(amountToSend);
        transcationHistoryForReciever.setUpdatedBalance(accountBalanceAfterAdding);
        updateForReciver.addToSet("transcation_history",transcationHistoryForReciever);

        Query queryforReciver=new Query();
        queryforReciver.addCriteria(Criteria.where("mobileNo").is(reciverDetails.getMobileNo()));

        //DB Operations

        UpdateResult updateResultforReciver=mongoTemplate.updateFirst(queryforReciver,updateForReciver,AccountDetails.class,"AccountDetails");
        UpdateResult updateResultForSender=mongoTemplate.updateFirst(queryforSender,updateForSender,AccountDetails.class,"AccountDetails");

    }

    public ResponseEntity<Object> addMoneyToWallect(String mobileNo, int amountToAdd) {

        Query query=new Query();
        query.addCriteria(Criteria.where("mobileNo").is(mobileNo));

        AccountDetails accountDetails=mongoTemplate.findOne(query,AccountDetails.class);

        int currentBalance=accountDetails.getAccountBalance();
        int updatedBalance=currentBalance+amountToAdd;

        TranscationHistory transcationHistory=new TranscationHistory();
        transcationHistory.setUpdatedBalance(updatedBalance);
        transcationHistory.setDeposit(amountToAdd);
        transcationHistory.setLastbalance(currentBalance);

        Update update=new Update();
        update.set("accountBalance",updatedBalance);
        update.addToSet("transcation_history",transcationHistory);

      UpdateResult updateResult=  mongoTemplate.updateFirst(query,update,AccountDetails.class);
      return new ResponseEntity<>("Money added  "+updateResult, HttpStatus.OK);

    }

    @Override
    public List<TranscationHistory> getAllTranscation(String mobileNo) {

     Criteria criteria=Criteria.where("mobileNo").is(mobileNo);
     Aggregation aggregation=Aggregation.newAggregation(
             Aggregation.match(criteria),
             Aggregation.project()
                     .and("mobileNo").as("mobileNo")
                     .and("accountNo").as("AccountNo")
                     .and("transcation_history").as("transcation_history")
     );
     List<Map<String, Object>> list = (List<Map<String, Object>>) mongoTemplate.aggregate(aggregation, "AccountDetails", Object.class).getRawResults().get("results");


     Map<String, Object> map=  list.get(0);
     List<TranscationHistory> transcationHistories= (List<TranscationHistory>) map.get("transcation_history");
     return  transcationHistories;

    }

}