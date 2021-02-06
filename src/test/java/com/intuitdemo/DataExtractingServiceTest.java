package com.intuitdemo;

import com.intuitdemo.model.Customer;
import com.intuitdemo.service.DataExtractingService;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;


class DataExtractingServiceTest {
    private Customer customer;
    ArrayList<Customer> customersAfterParsing;
    DataExtractingService dataExtractingService = new DataExtractingService();

    @Test
    void testParseJson() throws IOException, JSONException {
        addCustomerAPI();
        customersAfterParsing = dataExtractingService.jsonParser("https://fakebanky.herokuapp.com/transactions");
        for (int i = 0; i < customersAfterParsing.size(); i++) {
            if (customersAfterParsing.get(i).getAccountId().equals(customer.getAccountId())) {
                deepEqual(customersAfterParsing.get(i));
                System.out.println("The parsing is passed and the account that checked is : " + customersAfterParsing.get(i).getAccountId());
            }
        }
    }

    @Test
    void testParseWeb() throws IOException {
        addCustomerWeb();
        customersAfterParsing = dataExtractingService.webHtmlParser("https://fakebanky.herokuapp.com/fakebank");
        for (int i = 0; i < customersAfterParsing.size(); i++) {
            if (customersAfterParsing.get(i).getAccountId().equals(customer.getAccountId())) {
                deepEqual(customersAfterParsing.get(i));
                System.out.println("The parsing is passed and the account that checked is : " + customersAfterParsing.get(i).getAccountId());
            }
        }
    }

    private void addCustomerAPI() {
        customer = new Customer();
        customer.setAccountId("7b5092b8-73f1-471d-96d8-31b20dca913b");
        customer.setType("PERSONAL");
        Customer.transaction transaction = new Customer.transaction();
        ArrayList<Customer.transaction> transactions = new ArrayList<>();
        transaction.setTransactionId("21323424353");
        transaction.setAmount(12.45);
        transaction.setCurrency("USD");
        transactions.add(transaction);
        transaction = new Customer.transaction();
        transaction.setTransactionId("6342746827642876");
        transaction.setAmount(-67.13);
        transaction.setCurrency("GBP");
        transactions.add(transaction);
        transaction = new Customer.transaction();
        transaction.setTransactionId("58958088504");
        transaction.setAmount(987.5);
        transaction.setCurrency("NIS");
        transactions.add(transaction);
        customer.setTransactions(transactions);
    }

    private void addCustomerWeb() {
        customer = new Customer();
        customer.setAccountName("Dejuan Wintheiser (Tad)");
        customer.setAmount(25.78);
        customer.setCurrency("Dollar");
        customer.setAccountId("59472625");
        Customer.transaction transaction = new Customer.transaction();
        ArrayList<Customer.transaction> transactions = new ArrayList<>();
        transaction.setTransactionId("123");
        transaction.setDate("12-Nov-2019");
        transaction.setAmount(30);
        transaction.setCurrency("Dollar");
        transaction.setDescription("Fake");
        transactions.add(transaction);
        transaction = new Customer.transaction();
        transaction.setTransactionId("234");
        transaction.setDate("11-Oct-2019");
        transaction.setAmount(-5);
        transaction.setCurrency("Dollar");
        transaction.setDescription("Fake");
        transactions.add(transaction);
        transaction = new Customer.transaction();
        transaction.setTransactionId("656");
        transaction.setDate("21-Oct-2019");
        transaction.setAmount(100);
        transaction.setCurrency("Peso");
        transaction.setDescription("This is not real deat");
        transactions.add(transaction);
        customer.setTransactions(transactions);
    }

    private void deepEqual(Customer customerAfterParse) {
        Assert.assertEquals(customerAfterParse.getAccountId(), customer.getAccountId());
        Assert.assertEquals(customerAfterParse.getType(), customer.getType());
        Assert.assertEquals(customerAfterParse.getAccountName(), customer.getAccountName());
        Assert.assertTrue(customerAfterParse.getAmount() == customer.getAmount());
        Assert.assertEquals(customerAfterParse.getCurrency(), customer.getCurrency());
        for (int i = 0; i < customerAfterParse.getTransactions().size(); i++) {
            Assert.assertEquals(customerAfterParse.getTransactions().get(i).getTransactionId(), customer.getTransactions().get(i).getTransactionId());
            Assert.assertTrue(customerAfterParse.getTransactions().get(i).getAmount() == customer.getTransactions().get(i).getAmount());
            Assert.assertEquals(customerAfterParse.getTransactions().get(i).getCurrency(), customer.getTransactions().get(i).getCurrency());
            Assert.assertEquals(customerAfterParse.getTransactions().get(i).getDate(), customer.getTransactions().get(i).getDate());
            Assert.assertEquals(customerAfterParse.getTransactions().get(i).getDescription(), customer.getTransactions().get(i).getDescription());
        }

    }
}