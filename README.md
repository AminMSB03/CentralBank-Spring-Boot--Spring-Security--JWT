# CentralBank-Spring-Boot--Spring-Security--JWT

## Context of the project
For an urgent need, the central bank of Morocco will implement a service named "Bank Connect" to simplify and automate the banking system to Moroccans. This web application will allow to create an account for new customers, the latter is required to send a copy of CIN scanned via the registration form in addition to providing his name, address, email address and phone number to be confirmed by sms containing a 5-digit code valid for 3 minutes

Each new account is inactive, the agent who will take care (via his dashboard) of the activation of the opening of the bank accounts

Each customer has the choice to create a standard account or a professional account

- The Standard account will allow the customer to :

A Standard Visa ATM Card Make daily withdrawals with a limit of 5000 dhs/day and 100 000 dhs/year Make online purchases with the same limit

Payment of Telecom + Water and Electricity bills

- The Professional account will allow the customer to :

In addition to the advantages of the standard account, the customer will have a premium card with a withdrawal of 10,000 dhs/day and 200,000 dhs/year

Online purchases with a ceiling of 15 000 dhs/day/year

International card to make international purchases with a limit of 100 000 dhs/day/year

The 2 types of account are fed either by the customer himself (deposit at the agency) or by bank transfers by other customers, ie at the level of the customer dashboard, we can have the possibility of crediting another bank account, for that we must add the number of the beneficiary account of 24 digits and we specify the amount to send the sender account will receive by sms or email a confirmation code of the transaction, then the request is waiting to be validated by an agent of the central bank (already created in the system) which receives all the transfer requests sent and received by all the customers

For the payment of water and electricity or telecom bills, the customer must enter the contract number of the bill to be paid or the phone number if it is a phone bill, he will receive by email the payment bill.

## Required Work:

Create the appropriate class diagram Develop a Rest API using Spring Boot that respects the business rules mentioned above

Secure your API using Spring Security and JWT

Store your information in a PostgreSQL database
