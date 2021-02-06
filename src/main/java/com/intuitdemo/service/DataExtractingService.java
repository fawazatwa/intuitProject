package com.intuitdemo.service;

import com.intuitdemo.errorHandling.CustomerNotFoundException;
import com.intuitdemo.model.Customer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class DataExtractingService {

    private ArrayList<Customer> customers;

    public ArrayList<Customer> jsonParser(String url) throws IOException, JSONException {
        customers = new ArrayList<>();
        JSONObject json = new JSONObject(jsonExtracting(url));
        JSONArray account = json.getJSONArray("accounts");
        int numberOfAccounts = account.length();
        Customer customer;
        for (int i = 0; i < numberOfAccounts; ++i) {
            customer = new Customer();
            customer.setAccountId(account.getJSONObject(i).getString("account"));
            customer.setType(account.getJSONObject(i).getString("type"));
            customer.setAmount(account.getJSONObject(i).getDouble("amount"));
            customer.setCurrency(account.getJSONObject(i).getString("currency"));
            JSONArray transactionsFromJson = account.getJSONObject(i).getJSONArray("transactions");
            ArrayList<Customer.transaction> transactions = new ArrayList<>();
            for (int j = 0; j < transactionsFromJson.length(); ++j) {
                Customer.transaction transaction = new Customer.transaction();
                transaction.setTransactionId(transactionsFromJson.getJSONObject(j).getString("id"));
                transaction.setAmount(transactionsFromJson.getJSONObject(j).getDouble("amount"));
                transaction.setCurrency(transactionsFromJson.getJSONObject(j).getString("currency"));
                transactions.add(transaction);
            }
            customer.setTransactions(transactions);
            customers.add(customer);
        }
        return customers;
    }

    private String jsonExtracting(String url) throws IOException {
        Document resp = Jsoup.connect(url).ignoreContentType(true).get();
        String jsonString = resp.body().text();
        return jsonString;
    }

    public ArrayList<Customer> webHtmlParser(String url) throws IOException {
        customers = new ArrayList<>();
        Document resp = Jsoup.connect(url).ignoreContentType(true).get();
        String[] arr = resp.body().wholeText().split("\n");
        ArrayList<String> pageRows = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i].trim();
            if (!arr[i].equals("") && !arr[i].contains("GitHub"))
                pageRows.add(arr[i]);
        }
        for (int j = 0; j < pageRows.size(); j++) {
            System.out.println(pageRows.get(j));
        }
        Customer customer = null;
        for (int i = 0; i < pageRows.size(); i++) {


            if (pageRows.get(i).contains("Account name")) {

                customer = new Customer();
                customer.setAccountName(getAccountName(pageRows.get(i)));
                customer.setAmount(Double.parseDouble(getAmount(pageRows.get(i + 1))));
                customer.setCurrency(getCurrency(pageRows.get(i + 1)));
                customer.setAccountId(getAccountNumber(pageRows.get(i + 2)));
                i = i + 4;

                customer.setTransactions(new ArrayList<>());
                customers.add(customer);
            }
            if (customer != null && customer.getAccountId() != null) {
                Customer.transaction transaction = new Customer.transaction();
                transaction.setTransactionId(getTransactionId(pageRows.get(i)));
                transaction.setDate(getTransactionDate(pageRows.get(i + 1)));
                transaction.setAmount(Double.parseDouble(getAmount(pageRows.get(i + 2))));
                transaction.setCurrency(getCurrency(pageRows.get(i + 2)));
                transaction.setDescription(getTransactionDescription(pageRows.get(i + 3)));
                i = i + 3;
                customer.getTransactions().add(transaction);

            }


        }
        return customers;
    }

    private static String getAccountName(String name) {
        return name.split(":")[1].trim();
    }

    private static String getAmount(String balance) {
        return balance.split(":")[1].split(" ")[0].trim();
    }

    private static String getCurrency(String balance) {
        return balance.split(":")[1].split(" ")[1].trim();
    }

    private static String getAccountNumber(String accountNumber) {
        return accountNumber.split(":")[1].trim();
    }

    private static String getTransactionId(String transactionId) {
        return transactionId.split(":")[1].trim();
    }

    private static String getTransactionDate(String transactionDate) {
        return transactionDate.split(":")[1].trim();
    }

    private static String getTransactionBalance(String transactionBalance) {
        return transactionBalance.split(":")[1].trim();
    }

    private static String getTransactionDescription(String transactionDescription) {
        return transactionDescription.split(":")[1].trim();
    }

    public Customer getCustomerRequired(String id, String url) throws IOException, JSONException {
        ArrayList<Customer> dataAfterParsing = new ArrayList<>();
        Document resp = Jsoup.connect(url).ignoreContentType(true).get();

        String source = resp.body().text();
        if (source.startsWith("{")) {
            dataAfterParsing = jsonParser(url);
        } else {
            dataAfterParsing = webHtmlParser(url);
        }
        for (int i = 0; i < dataAfterParsing.size(); i++) {
            if (dataAfterParsing.get(i).getAccountId().equals(id))
                return dataAfterParsing.get(i);
        }

        throw new CustomerNotFoundException("The customer does not exist!!!");
    }


}
