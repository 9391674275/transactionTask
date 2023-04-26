package com.task.transactiontask.model;

import java.io.Serializable;

public class Transaction  implements Serializable {

    private String transactionStatus;
    private String amount;
    private String transactionDate;
    private String narration;

    public Transaction(String transactionStatus, String amount, String transactionDate, String narration) {
        this.transactionStatus = transactionStatus;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.narration = narration;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getNarration() {
        return narration;
    }
}