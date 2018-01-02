import java.io.*;
import java.net.*;
import java.util.*;
import requesttypes.*;
import responsetypes.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Processor implements Runnable {
	private int id;
	BankClient bc;
	ArrayList<Integer> accList;
	Random rand = new Random();
	PrintWriter writer;
	String hostname;
	int portNumber;

	Processor (int id, BankClient b, ArrayList<Integer> accList, PrintWriter writer, String hostname, int portNumber) {
		this.id = id;
		bc = b;
		this.accList = accList;
		this.writer = writer;
		this.hostname = hostname;
		this.portNumber = portNumber;
	}
	
	public void run() {
		System.out.println("Starting "+id);
		
		int a = rand.nextInt(accList.size());
		int b = rand.nextInt(accList.size());

		try {
			Socket echoSocket = new Socket (hostname, portNumber);
			OutputStream out = echoSocket.getOutputStream();
			InputStream in = echoSocket.getInputStream();

			bc.transferRequest (out, in, writer, accList.get(a), accList.get(b), 10);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Completed "+id);
	}
}

class BankClient {
	
	private ArrayList<Integer> accountlist = new ArrayList<Integer>();
	private static Random rand = new Random();

	ArrayList<Integer> getAccountList () {
		return accountlist;
	}

	void newAccountRequest (OutputStream out, InputStream in, PrintWriter writer) throws IOException{

		try {
			NewAccountRequest accreq = new NewAccountRequest("NewAccountRequest");
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(accreq);
			ObjectInputStream oin = new ObjectInputStream(in);
			Response resp = (Response)oin.readObject();
			NewAccountCreationResponse accresp = (NewAccountCreationResponse)resp;
			writer.println("Request Name: "+ accresp.getReqName());
			writer.println("Returned UID: "+ accresp.getResponse());
			writer.println();	
			accountlist.add(accresp.getResponse());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void depositRequest (OutputStream out, InputStream in, PrintWriter writer, int uID, int amount) throws IOException{
		
		try {
			DepositRequest depreq = new DepositRequest("DepositRequest", uID, amount);
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(depreq);
			ObjectInputStream oin = new ObjectInputStream(in);
			Response resp = (Response)oin.readObject();
			DepositResponse depresp = (DepositResponse)resp;
			writer.println("Request Name: "+ depresp.getReqName());
			writer.println("Returned Status: "+ depresp.getResponse());
			writer.println();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	int balanceRequest (OutputStream out, InputStream in, PrintWriter writer, int uID) throws IOException{
		
		try {

			BalanceRequest balreq = new BalanceRequest("BalanceRequest", uID);
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(balreq);
			ObjectInputStream oin = new ObjectInputStream(in);
			Response resp = (Response)oin.readObject();
			BalanceResponse balresp = (BalanceResponse)resp;
			writer.println("Request Name: "+ balresp.getReqName());
			writer.println("Returned Balance: "+ balresp.getResponse());	
			return balresp.getResponse();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}

	void transferRequest (OutputStream out, InputStream in, PrintWriter writer, int srcuID, int targuID, int amount) throws IOException {
		
		try {

			TransferRequest transreq = new TransferRequest("TransferRequest", srcuID, targuID, amount);
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(transreq);
			ObjectInputStream oin = new ObjectInputStream(in);
			Response resp = (Response)oin.readObject();
			TransferResponse transresp = (TransferResponse)resp;

			if (transresp.getResponse() == "FAILED") {
				writer.println("Request Name: "+ transresp.getReqName());
				writer.println("Returned Status: "+ transresp.getResponse());
				writer.println("SRC ID: "+ srcuID + "TARG ID: " + targuID);	
			} else {
				writer.println("Request Name: "+ transresp.getReqName());
				writer.println("Returned Status: "+ transresp.getResponse());	
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	void processTask () {

	}

	public static void main (String [] args) {

		String hostname = args[0];
		int portNumber = Integer.parseInt(args[1]);
		int threadCount = Integer.parseInt(args[2]);
		int iterationCount = Integer.parseInt(args[3]);
		BankClient bankclient = new BankClient();

		try {
			Socket echoSocket = new Socket (hostname, portNumber);
			OutputStream out = echoSocket.getOutputStream();
			InputStream in = echoSocket.getInputStream();

			PrintWriter writer = new PrintWriter("clientLog.txt", "UTF-8");	

			bankclient.newAccountRequest(out, in, writer);

			//Creating 100 accounts on the server
			for (int i = 0; i < 100; i++)
				bankclient.newAccountRequest (out, in, writer);

			//Depositing 100 in each of those accounts
			for (int i = 0; i < 100; i++) {
				
				ArrayList<Integer> list = bankclient.getAccountList();
				int accid = list.get(i);
				bankclient.depositRequest(out, in, writer, accid, 100);
			}

			int sum = 0;
			//Getting balance of 100 accounts and summing
			for (int i = 0; i < 100; i++) {
				ArrayList<Integer> list = bankclient.getAccountList();
				int accid = list.get(i);
				int bal = bankclient.balanceRequest(out, in, writer, accid);

				if (bal == -1)
					bal = 0;

				sum += bal;
			}

			System.out.println("Sum of balances = " + sum);
			writer.println("Sum of balances = " + sum);
			writer.println();

			//Creating diffeent threads for executing the same task n times
			/*ExecutorService executor = Executors.newFixedThreadPool(threadCount);

			for (int i = 0; i < iterationCount; i++) {
				executor.submit(new Processor(i, bankclient, bankclient.getAccountList(), writer, hostname, portNumber));
			}
				
			executor.shutdown();

			try {
				executor.awaitTermination(1, TimeUnit.DAYS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/

			for (int i = 0; i < threadCount; i++) {

				new Thread () {
					public void run () {
						System.out.println("TransferRequest");
						int a = rand.nextInt(bankclient.getAccountList().size());
						int b = rand.nextInt(bankclient.getAccountList().size());

						try {
							Socket echoSocket = new Socket (hostname, portNumber);
							OutputStream out = echoSocket.getOutputStream();
							InputStream in = echoSocket.getInputStream();

							for (int j = 0; j < iterationCount; j++) {
								ArrayList<Integer> arr = bankclient.getAccountList();
								bankclient.transferRequest (out, in, writer, arr.get(a), arr.get(b), 10);	
							}

						echoSocket.close();

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
			
			sum = 0;

			//Getting balance of 100 accounts and summing
			 try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			for (int i = 0; i < 100; i++) {
				ArrayList<Integer> list = bankclient.getAccountList();
				int accid = list.get(i);
				int bal = bankclient.balanceRequest(out, in, writer, accid);

				if (bal == -1)
					bal = 0;

				sum += bal;
			}


			System.out.println("Sum of balances = " + sum);
			writer.println("Sum of balances = " + sum);
			writer.println();
			echoSocket.close();			
			writer.close();
		}catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostname);
            System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " +
                hostname);
            System.exit(1);
		} 
	}
}
