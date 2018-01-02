# A Multi-Threaded Bank Server Demonstrating Object Serialization-Deserialization In Java 

## Abstract:

A multi-threaded server for a bank to store the account records for a set of people. The server will then serve multiple client requests concurrently.

The Account objects will contain the following fields:<br />
Class Account:
  * UID: Integer
  * balance: Integer
Each Account will have a unique UID.

## Supported Operations:
* CreateAccount: This will insert a new account object in the server’s hashtable. Initial balance will be zero.
  * input parameter: nothing
  * output parameter: UID for the new account will be returned by this parameter
* Deposit:This will add a specified amount (positive integer) to the specified account.
  * input parameters: account UID and a positive Integer value for amount to be deposited
  * output parameter: status (OK, or FAILED)
* GetBalance: This will get the balance associated with a particular account
  * input parameters: account UID
  * output parameter: Account balance (OK, or FAILED)
* Transfer: This will transfer money between two accounts given two account ids.
  * input parameters: Source account UID, Target account UI, Amount to be transferred
  * output parameter: status (OK, or FAILED)
  
The object serialization has been performed using two approaches:
 * Socket Programming in Java
 * Using Apache Thrift to serve the client requests and hence using Thrift Definition language to define the object interfaces for communication

