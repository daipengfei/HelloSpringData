package com.april.fourth.entity;

import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by daipengfei
 * on 2017/4/12.
 */

@Document(indexName = "bank")
public class Bank {
   private String accountNumber;

   public String getAccountNumber() {
      return accountNumber;
   }

}
