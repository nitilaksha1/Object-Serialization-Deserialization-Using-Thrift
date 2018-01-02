import account.*;
import java.net.*;
import java.io.*;
import java.util.*;
import requesttypes.*;
import responsetypes.*;

class Node{
	int countPlus;
	int countMinus;

	Node(int aa,int bb){
		countPlus = aa;
		countMinus = bb;
	}
}

public class BankServer {
	
	private HashMap<Integer, Account> map = new HashMap<Integer, Account>();
	public static HashMap<Integer,Node> ola = new HashMap<Integer,Node>();
	private int accountid = 1;
	private Object lock = new Object();

	int createAccount () {
		Account acc = new Account(accountid, 0);
		map.put(accountid, acc);
		accountid++;
		return acc.getUID();
	}

	String deposit (int uID, int amount) {

		if (map.containsKey(uID)) {

			Account acc = map.get(uID);
			acc.deposit(amount);
			return "OK";
		}

		return "FAILED";
	}

	int getBalance (int uID) {

		if (map.containsKey(uID)) {

			Account acc = map.get(uID);
			return acc.getBalance();
		}		

		return -1;
	}

	String transfer (int srcuID, int targuID, int amount) {
		
		if (map.containsKey(srcuID) && map.containsKey(targuID)) {

			Account acc1 = map.get(srcuID);
			Account acc2 = map.get(targuID);

			if(ola.containsKey(srcuID)){
				Node temp = ola.get(srcuID);
				temp.countMinus = temp.countMinus - 1;
				ola.put(srcuID,temp);
			}else{
				Node temp = new Node(0,-1);
				ola.put(srcuID,temp);

			}

			if(ola.containsKey(targuID)){
				Node temp = ola.get(targuID);
				temp.countPlus = temp.countPlus + 1;
				ola.put(targuID,temp);
			}else{
				Node temp = new Node(1,0);
				ola.put(targuID,temp);

			}

			synchronized (lock) {
				
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
			}	
		}
		System.out.println("No Accounts found");
		return "FAILED";		
	}

	void cleanup (ServerSocket socket) {

		try {

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void processRequest (String reqName) {
		switch (reqName) {

			case "NewAccountRequest":
				NewAccountRequest accreq = (NewAccountRequest)req;
				int uid = bs.createAccount();
				NewAccountCreationResponse resp = new NewAccountCreationResponse(accreq.getReqName(), uid);
				ObjectOutputStream os = new ObjectOutputStream(out);
				os.writeObject(resp);
				System.out.println("Response sent");
			break;


			case "DepositRequest":
				DepositRequest depreq = (DepositRequest)req;
				String stat = bs.deposit(depreq.getAccUID(), depreq.getAmount());
				System.out.println("Money Deposited");
				DepositResponse resp1 = new DepositResponse(depreq.getReqName(), stat);
				System.out.println("Response object created");
				ObjectOutputStream os1 = new ObjectOutputStream(out);
				os1.writeObject(resp1);
				System.out.println("Response sent");
			break;

			case "BalanceRequest":
				BalanceRequest balreq = (BalanceRequest)req;
				int bal = bs.getBalance(balreq.getAccUID());

				BalanceResponse resp2 = new BalanceResponse(balreq.getReqName(), bal);
				ObjectOutputStream os2 = new ObjectOutputStream(out);
				os2.writeObject(resp2);
			break;

			case "TransferRequest":
				TransferRequest transreq = (TransferRequest)req;
				String stat1 = bs.transfer(transreq.getSourceAccUID(), transreq.gettargetAccUID(), transreq.getAmount());

				System.out.println("Money Transferred");

				TransferResponse resp3 = new TransferResponse(transreq.getReqName(), stat1);
				ObjectOutputStream os3 = new ObjectOutputStream(out);
				os3.writeObject(resp3);

				System.out.println("Response sent");
				for(Map.Entry<Integer,Node> entry : ola.entrySet()){
					System.out.println(entry.getKey()+" : "+entry.getValue().countPlus+" : "+entry.getValue().countMinus);
					}
			break;									
		}
	}

	public static void main (String [] args) {
		
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);
		BankServer bs = new BankServer();
		ServerSocket echoserver = null;

		try {
			echoserver = new ServerSocket (portNumber);	

			while (true) {
				Socket clientsocket = echoserver.accept();


				Thread t = new Thread () {

					public void run () {

						try {
							while(true){

								InputStream in  = clientsocket.getInputStream();
						        OutputStream out = clientsocket.getOutputStream();
								ObjectInputStream oin;
								if(true){
									oin = new ObjectInputStream(in);	
								}else{
									break;
								}
								
								Request req = (Request)oin.readObject();
								String str = req.getReqName();
								processRequest(str);
							}

						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
						
					}	
				};

				t.start();
			
	
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
        	System.out.println(e.getMessage());
        	bs.cleanup(echoserver);	
		}
	}
}


