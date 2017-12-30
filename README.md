# A Multi-Threaded Bank Server Demonstrating Object Serialization-Deserialization Using Apache Thrift 

<br />
Abstract:

A multi-threaded server for a bank to store the account records for a set of people. The server will maintain this<br /> data in the primary memory, in a hashtable. The Account objects stored in the hashtble will contain the following fields:<br />
Class Account:
  * UID: Integer
  * balance: Integer
<br />
Each Account will have a unique UID, which will be used as a key for storing data in the hashtable of the server. The server supports four operations:
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

