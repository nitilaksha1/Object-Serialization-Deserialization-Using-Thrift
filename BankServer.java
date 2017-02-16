import account.*;
import java.net.*;
import java.io.*;
import java.util.*;
import requesttypes.*;
import responsetypes.*;

class BankServer {
	
	private HashMap<Integer, Account> map = new HashMap<Integer, Account>();
	private int accountid = 1;

	int NewAccountCreation () {
		Account acc = new Account(accountid, 0);
		map.put(accountid, acc);
		accountid++;
		return acc.getUID();
	}

	public static void main (String [] args) {
		
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);
		BankServer bs = new BankServer();

		try (ServerSocket echoserver = new ServerSocket (portNumber);
		Socket clientsocket = echoserver.accept();
		InputStream in  = clientsocket.getInputStream();
		PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);) {
			
			ObjectInputStream oin = new ObjectInputStream(in);

			try {
				if (((Request)oin.readObject()).getReqName().equals("NewAccountCreation")) {
					System.out.println("Request for new account creation");
					bs.NewAccountCreation();

				} else {
					System.out.println("A different request came in");
				}
				/*date = (Date)oin.readObject();
				message= (String) oin.readObject();
				out.println("Echo: date = "+ date+ " message = " + message);*/
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Exception caught when trying to listen on port "
            + portNumber + " or listening for a connection");
        	System.out.println(e.getMessage());
		}	

		
	}
}


