package com.intuitdemo.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

public class Customer {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountName;
    private double amount;
    private String currency;
    private String accountId;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private String type;
    private ArrayList<transaction> transactions;

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTransactions(ArrayList<transaction> transactions) {
        this.transactions = transactions;
    }

    public String getAccountName() {
        return accountName;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getType() {
        return type;
    }

    public ArrayList<transaction> getTransactions() {
        return transactions;
    }

    public static class transaction {
        String transactionId;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String date;
        double amount;
        String currency;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String description;

        public String getTransactionId() {
            return transactionId;
        }

        public String getDate() {
            return date;
        }

        public double getAmount() {
            return amount;
        }

        public String getCurrency() {
            return currency;
        }

        public String getDescription() {
            return description;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
