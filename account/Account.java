package account;

class Account {
	int UID;
	int balance;

	Account (int UID, int balance) {
		this.UID = UID;
		this.balance = balance;
	}

	void deposit (int amount) {
		balance += amount;
	}

	int getBalance () {
		return balance;
	}
}