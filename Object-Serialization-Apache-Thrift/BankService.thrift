
service BankService {

   i32 createAccount(),
   String deposit (1:i32 uID, 2:i32 amount),
   i32 getBalance (1:i32 uID),
   String transfer (1:i32 srcuID, 2:i32 targuID, 3:i32 amount)

}

