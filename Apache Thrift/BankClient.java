import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import java.io.*;
import java.net.*;
import java.util.*;



Public class BankClient {
	  private ArrayList<Integer> accountlist = new ArrayList<Integer>();

	  ArrayList<Integer> getAccountList () 
	  {
		return accountlist;
	  }




	  void newAccountRequest(BankService.Client client, PrintWriter writer) throws TException{
	  	//client.createAccount();
	  	System.out.println(client.createAccount());
	  }

	  void depositRequest(BankService.Client client, PrintWriter writer, int id, int amount) throws TException{
	  	//client.deposit();
	  	System.out.println(client.deposit());
	  }

	  int balanceRequest(BankService.Client client, PrintWriter writer, int id) throws TException{
	  	//client.getBalance();
	  	System.out.println(client.getBalance());
	  }

	  void transferRequest(BankService.Client client, PrintWriter writer, int src, int target, int amount) throws TException{
	  	//client.transfer();
	  	System.out.println(client.getBalance(client.transfer()));
	  }


	public static void main(String[] args){
	  
	  if(args.length != 2){
	  	System.out.println("Required 2 arguments: Hostname and Portname");
	  	return;
	  }


	  
	  private static Random rand = new Random();
	  String hostname = args[0];
	  int portname = Integer.parseInt(args[1]);
	  BankClient bc = new BankClient();

	  try{
	  		TTransport transport;
			transport = new TSocket(hostname, portname);
  			transport.open();

  			TProtocol protocol = new  TBinaryProtocol(transport);
  			BankService.Client client = BankService.Client(protocol);
  			PrintWriter writer = new PrintWriter("clientLog.txt", "UTF-8");
  			
  			newAccountRequest(client,writer);

  			depositRequest(client,writer,0,10);

  			int bal = balanceRequest(client,writer,0);

  			transferRequest(client,writer,0,1,10);
	
	  }catch(TException e){
	  	e.printStackTrace();
	  }
	  
      

	}
}