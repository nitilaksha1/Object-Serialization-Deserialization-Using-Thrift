import account.*;
import java.net.*;
import java.io.*;
import java.util.*;
import requesttypes.*;
import responsetypes.*;

public class BankServer {
	
	private HashMap<Integer, Account> map = new HashMap<Integer, Account>();
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

			synchronized (lock) {
				
				if ((acc1.getBalance() - amount) < 0) {
					return "FAILED";
				}

				acc1.withdraw(amount);
				acc2.deposit(amount);

				return "OK";	
			}	
		}

		return "FAILED";		
	}

	void cleanup (ServerSocket socket) {

		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
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
				System.out.println("Accepting client connection");

				System.out.println(Thread.currentThread().getName());

				Thread t = new Thread () {

					public void run () {

						try {
							while(true){

								InputStream in  = clientsocket.getInputStream();
						        OutputStream out = clientsocket.getOutputStream();
								System.out.println("I/O streams created");
								ObjectInputStream oin;
								System.out.println(in.available());
								if(true){
									oin = new ObjectInputStream(in);	
								}else{
									break;
								}
								
								System.out.println("ObjectInputStream created");
								Request req = (Request)oin.readObject();
								System.out.println("Object Read");
								String str = req.getReqName();
								System.out.println(str);

								switch (str) {
									case "NewAccountRequest":
										NewAccountRequest accreq = (NewAccountRequest)req;
										int uid = bs.createAccount();
										System.out.println("Account created");
										NewAccountCreationResponse resp = new NewAccountCreationResponse(accreq.getReqName(), uid);
										System.out.println("Response object created");
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

										System.out.println("Money Deposited");
										BalanceResponse resp2 = new BalanceResponse(balreq.getReqName(), bal);
										System.out.println("Response object created");
										ObjectOutputStream os2 = new ObjectOutputStream(out);
										os2.writeObject(resp2);
										System.out.println("Response sent");
									break;

									case "TransferRequest":
										TransferRequest transreq = (TransferRequest)req;
										String stat1 = bs.transfer(transreq.getSourceAccUID(), transreq.gettargetAccUID(), transreq.getAmount());

										System.out.println("Money Transferred");

										TransferResponse resp3 = new TransferResponse(transreq.getReqName(), stat1);
										System.out.println("Response object created");
										ObjectOutputStream os3 = new ObjectOutputStream(out);
										os3.writeObject(resp3);

										System.out.println("Response sent");
									break;									
								}	
	
							}

						} catch (IOException e) {
							//e.printStackTrace();
						} catch (ClassNotFoundException e) {
							//e.printStackTrace();
						}

						String threadName = Thread.currentThread().getName();
						System.out.println(threadName);
						System.out.println("Thread done");
						
					}	
				};

				t.start();

				System.out.println("Ready to accept new connnection");
						
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
        	System.out.println(e.getMessage());
        	bs.cleanup(echoserver);	
		}
		

		/*try (ServerSocket echoserver = new ServerSocket (portNumber);
		Socket clientsocket = echoserver.accept();
		) {
			
			ObjectInputStream oin = new ObjectInputStream(in);

			try {
				if (((Request)oin.readObject()).getReqName().equals("NewAccountCreation")) {
					System.out.println("Request for new account creation");
					ObjectOutputStream os = new ObjectOutputStream(out);
					int uid = bs.NewAccountCreation();
					NewAccountCreationResponse resp = new NewAccountCreationResponse("NewAccountCreation", uid);
					System.out.println("UID = "+ resp.getResponse());
					os.writeObject(resp);

				} else {
					System.out.println("A different request came in");
				}
				/*date = (Date)oin.readObject();
				message= (String) oin.readObject();
				out.println("Echo: date = "+ date+ " message = " + message);*/
			/*} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
        	System.out.println(e.getMessage());
		}*/

		
	}
}


