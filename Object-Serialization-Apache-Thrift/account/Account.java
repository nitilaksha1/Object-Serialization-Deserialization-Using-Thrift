package account;

class Account {
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