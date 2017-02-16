import account.*;
import java.net.*;
import java.io.*;

class BankServer {
	
	public static void main (String [] args) {
		
		if (args.length != 1) {
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);

		try (ServerSocket echoserver = new ServerSocket (portNumber);
			Socket clientsocket = echoserver.accept();
			PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader (new InputStreamReader(clientsocket.getInputStream()));) {

				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					out.println(inputLine);
				}

		} catch (IOException e) {
				System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            	System.out.println(e.getMessage());
		}
	}
}


