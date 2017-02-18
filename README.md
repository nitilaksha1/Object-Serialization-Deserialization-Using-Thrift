# DistributedSystemsProj1

<br />
Abstract:

Server is a multi-threaded server for a bank to store the account records for a set of people. The server will maintain this<br /> data in the primary memory, in a hashtable. The Account objects stored in the hashtble will contain the following fields:<br />
Class Account:
  * UID: Integer
  * balance: Integer
<br />
Each Account will have a unique UID, which will be used as a key for storing data in the hashtable of the server. The server <br />suppors four operations:<br />
1. CreateAccount:This will insert a new account object in the server’s hashtable. Initial balance will be zero.<br /> input parameter: nothing<br />
output parameter: UID for the new account will be returned by this parameter.<br />
2. Deposit:This will add a specified amount (positive integer) to the specified account.<br />
input parameters: account UID and a positive Integer value for amount to be deposited.<br /> output parameter: status (OK, or FAILED)<br />
3. GetBalance:<br />
4. Transfer: <br />

Done:

->Basic Communication and object passing between client achieved<br />
->Request classes created<br />
->Response classes created<br /> 
<br />
TODO:<br />
<br />
->Start with creating 1000 account creations and 1000 deposits<br />
->Creating thread pool<br />
->Defining task for each thread<br />
->Achieving synchronization in communication with server<br />
