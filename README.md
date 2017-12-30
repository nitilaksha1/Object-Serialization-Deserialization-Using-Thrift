# A Multi-Threaded Bank Server Demonstrating Object Serialization-Deserialization Using Apache Thrift 

<br />
Abstract:

A multi-threaded server for a bank to store the account records for a set of people. The server will maintain this<br /> data in the primary memory, in a hashtable. The Account objects stored in the hashtble will contain the following fields:<br />
Class Account:
  * UID: Integer
  * balance: Integer
<br />
Each Account will have a unique UID, which will be used as a key for storing data in the hashtable of the server. The server supports four operations:<br />
* CreateAccount:This will insert a new account object in the server’s hashtable. Initial balance will be zero.<br /> input parameter: nothing<br />
output parameter: UID for the new account will be returned by this parameter.<br />
* Deposit:This will add a specified amount (positive integer) to the specified account.<br />
input parameters: account UID and a positive Integer value for amount to be deposited.<br /> output parameter: status (OK, or FAILED)
* GetBalance: This will get the balance associated with a particular account
* Transfer: This will transfer money between two accounts given two account ids.
