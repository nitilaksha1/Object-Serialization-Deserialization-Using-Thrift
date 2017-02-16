import java.io.*;
import java.net.*;
import java.util.*;
import requesttypes.*;
import responsetypes.*;

class BankClient {
	
	public static void main (String [] args) {

		String hostname = args[0];
		int portNumber = Integer.parseInt(args[1]);
		String userInput;

		try(Socket echoSocket = new Socket (hostname, portNumber);
			OutputStream out = echoSocket.getOutputStream();
			BufferedReader in = new BufferedReader (new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader (new InputStreamReader (System.in))) {
			
			ObjectOutputStream os = new ObjectOutputStream(out);
			
			os.writeObject(new Request("NewAccountCreation"));
			//os.writeObject( "Now I understand this example of object serialization.");
			//System.out.println (in.readLine());	
			

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