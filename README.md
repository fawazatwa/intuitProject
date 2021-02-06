# Data Aggregation
The purpose of the project is to get data from two resources and to aggregate them in Customer objects and to return the balance of the customer account and the transactions that he did. 
   Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
## Prerequisites
- You need to have any JAVA IDE to open the project (Intelleji is recommended).
- Postman to send requests  

## Running the Project
- Run the main class “IntuitdemoApplication” in order to run the server.
- When the server is up, go to “postman” and try to run the requests.
- You need to register in order to get data and details.
- If you are not registered, you can’t get any data from other accounts, you need to be registered and using the same username.

### Example:
You can run this command in postman: 
```
localhost:8080/aggregation-data?url=http://localhost:8080/file2.json&id=7b5092b8-73f1-471d-96d8-31b20dca913b&username=fawaz
```
You will get the following response: 
```
{
   "amount": 30000.0,
   "currency": "EURO",
   "accountId": "7b5092b8-73f1-471d-96d8-31b20dca913b",
   "type": "PERSONAL",
   "transactions": [
       {
           "transactionId": "21323424353",
           "amount": 12.45,
           "currency": "USD"
       },
       {
           "transactionId": "6342746827642876",
           "amount": -67.13,
           "currency": "GBP"
       },
       {
           "transactionId": "58958088504",
           "amount": 987.5,
           "currency": "NIS"
       }
   ]
}
```
## Running the tests
you should run this class DataExtractingServiceTest
 
## Built With
- Spring - Application framework 
- Maven - Dependency Management
