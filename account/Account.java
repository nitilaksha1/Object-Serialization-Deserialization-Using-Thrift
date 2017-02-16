package account;

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

	public int getBalance () {
		return balance;
	}
}