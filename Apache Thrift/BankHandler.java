import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import account.*;

public class BankHandler implements BankService.Iface {
	private HashMap<Integer, Account> map = new HashMap<Integer, Account>();
	private int accountid = 1;
	private Object lock = new Object();

	@Override
	public int createAccount() throws TException {
        Account acc = new Account(accountid, 0);
		map.put(accountid, acc);
		accountid++;
		return acc.getUID();
	}

	@Override
	String deposit (int uID, int amount) {
		if (map.containsKey(uID)) {

			Account acc = map.get(uID);
			acc.deposit(amount);
			return "OK";
		}

		return "FAILED";
	}

	@Override
	int getBalance (int uID) {

		if (map.containsKey(uID)) {

			Account acc = map.get(uID);
			return acc.getBalance();
		}		

		return -1;	
	}

	@Override
	String transfer (int srcuID, int targuID, int amount) {
		if (map.containsKey(srcuID) && map.containsKey(targuID)) {

			Account acc1 = map.get(srcuID);
			Account acc2 = map.get(targuID);

		//	synchronized (lock) {
				
				if ((acc1.getBalance() - amount) < 0) {
					System.out.println("Acc1: "+ srcuID + " Acc2: "+ targuID);
					System.out.println("Inside if transfer: "+ " Amount: "+ amount+ " Banalnce: "+ acc1.getBalance());
					return "FAILED";
				}

				System.out.println("Acc1: "+ srcuID + " Acc2: "+ targuID);
				System.out.println("Outside if transfer: "+ " Amount: "+ amount+ " Banalnce: "+ acc1.getBalance());

				acc1.withdraw(amount);
				acc2.deposit(amount);

				return "OK";	
			//}	
		}

		System.out.println("No Accounts found");
		return "FAILED";
	}