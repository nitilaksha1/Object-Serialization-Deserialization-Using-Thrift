import java.io.*;
import java.net.*;
import java.util.*;
import requesttypes.*;
import responsetypes.*;

class BankClient {
	
	private ArrayList<Integer> accountlist = new ArrayList<Integer>();

	ArrayList<Integer> getAccountList () {
		return accountlist;
	}

	void newAccountRequest (OutputStream out, InputStream in, PrintWriter writer) throws IOException{

		try {
			System.out.println("Inside newAccountRequest");
			NewAccountRequest accreq = new NewAccountRequest("NewAccountRequest");
			System.out.println("Created Account Request");
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(accreq);
			System.out.println("Sent Account Request");
			ObjectInputStream oin = new ObjectInputStream(in);
			System.out.println("ObjectInputStream created");
			Response resp = (Response)oin.readObject();
			NewAccountCreationResponse accresp = (NewAccountCreationResponse)resp;
			writer.println("Request Name: "+ accresp.getReqName());
			System.out.println("Request Name: "+ accresp.getReqName());
			writer.println("Returned UID: "+ accresp.getResponse());
			System.out.println("Returned UID: "+ accresp.getResponse());
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
			System.out.println("Request Name: "+ depresp.getReqName());
			writer.println("Returned Status: "+ depresp.getResponse());
			System.out.println("Returned Status: "+ depresp.getResponse());
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
			System.out.println("Request Name: "+ balresp.getReqName());
			writer.println("Returned Balance: "+ balresp.getResponse());	
			System.out.println("Returned Balance: "+ balresp.getResponse());
			return balresp.getResponse();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}

	void processTask () {

	}

	public static void main (String [] args) {

		String hostname = args[0];
		int portNumber = Integer.parseInt(args[1]);
		//int threadCount = Integer.parseInt(args[2]);
		//int iterationCount = Integer.parseInt(args[3]);
		BankClient bankclient = new BankClient();

		try {
			Socket echoSocket = new Socket (hostname, portNumber);
			System.out.println("client socket created");

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

			writer.close();
			echoSocket.close();

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
