import java.io.*;
import java.net.*;

class BankClient {
	
	public static void main (String [] args) {

		String hostname = args[0];
		int portNumber = Integer.parseInt(args[1]);
		String userInput;

		try(Socket echoSocket = new Socket (hostname, portNumber);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader (new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader (new InputStreamReader (System.in))) {

			while ((userInput = stdIn.readLine()) != null) {
				out.println(userInput);
				System.out.println ("echo: " + in.readLine());
			}

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