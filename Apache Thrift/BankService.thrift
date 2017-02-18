public class Account {
	int UID;
	int balance;

	public Account (int UID, int balance) {
		this.UID = UID;
		this.balance = balance;
	}

	public int getUID () {
		return UID;
	}

	public void deposit (int amount) {
		balance += amount;
	}

	public void withdraw (int amount) {

		if ((balance - amount) < 0)
			return;

		balance -= amount;
	}

	public int getBalance () {
		return balance;
	}

}

service BankService {

   i32 createAccount(),
   String deposit (1:i32 uID, 2:i32 amount),
   i32 getBalance (1:i32 uID),
   String transfer (1:i32 srcuID, 2:i32 targuID, 3:i32 amount)

}

