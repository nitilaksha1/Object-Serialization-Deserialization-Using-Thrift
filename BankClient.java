import java.io.*;
import java.net.*;
import java.util.*;
import requesttypes.*;
import responsetypes.*;

class BankClient {
	
	private ArrayList<Integer> accountlist;

	ArrayList<Integer> getAccountList () {
		return accountlist;
	}

	void newAccountRequest (ObjectOutputStream os, ObjectInputStream oin, PrintWriter writer) throws IOException{

		NewAccountRequest accreq = new NewAccountRequest("NewAccountRequest");
		os.writeObject(accreq);
		try {
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

	void depositRequest (ObjectOutputStream os, ObjectInputStream oin, PrintWriter writer, int uID, int amount) throws IOException{
		
		DepositRequest depreq = new DepositRequest("DepositRequest", uID, amount);
		os.writeObject(depreq);

		try {
			Response resp = (Response)oin.readObject();
			DepositResponse depresp = (DepositResponse)resp;
			writer.println("Request Name: "+ depresp.getReqName());
			writer.println("Returned Status: "+ depresp.getResponse());	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void balanceRequest (ObjectOutputStream os, ObjectInputStream oin, PrintWriter writer, int uID) throws IOException{
		
		BalanceRequest balreq = new BalanceRequest("BalanceRequest", uID);
		os.writeObject(balreq);

		try {
			Response resp = (Response)oin.readObject();
			BalanceResponse balresp = (BalanceResponse)resp;
			writer.println("Request Name: "+ balresp.getReqName());
			writer.println("Returned Balance: "+ balresp.getResponse());	
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

		try(Socket echoSocket = new Socket (hostname, portNumber);
			OutputStream out = echoSocket.getOutputStream();
			InputStream in = echoSocket.getInputStream();
			BufferedReader stdIn = new BufferedReader (new InputStreamReader (System.in))) {

			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(new Request("NewAccountCreation"));
			ObjectInputStream oin = new ObjectInputStream(in);

			PrintWriter writer = new PrintWriter("clientLog.txt", "UTF-8");	

			//Creating 100 accounts on the server
			for (int i = 0; i < 100; i++)
				bankclient.newAccountRequest (os, oin, writer);
			
			//Depositing 100 in each of those accounts
			for (int i = 0; i < 100; i++) {
				ArrayList<Integer> list = bankclient.getAccountList();
				int accid = list.get(i);
				bankclient.depositRequest(os, oin, writer, accid, 100);
			}

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