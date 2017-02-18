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
	  	System.out.println(client.deposit(id,amount));
	  }

	  int balanceRequest(BankService.Client client, PrintWriter writer, int id) throws TException{
	  	//client.getBalance();
	  	System.out.println(client.getBalance(id));
	  }

	  void transferRequest(BankService.Client client, PrintWriter writer, int src, int target, int amount) throws TException{
	  	//client.transfer();
	  	System.out.println(client.transfer(src,target,amount));
	  }


	public static void main(String[] args){
	  
	  if(args.length != 2){
	  	System.out.println("Required 2 arguments: Hostname and Portname");
	  	return;
	  }


	  
	  private static Random rand = new Random();
	  String hostname = args[0];
	  int portname = Integer.parseInt(args[1]);
	  int threadCount = Integer.parseInt(args[2]);
	  int iterationCount = Integer.parseInt(args[3]);

	  BankClient bc = new BankClient();

	  try{
	  		TTransport transport;
			transport = new TSocket(hostname, portname);
  			transport.open();

  			TProtocol protocol = new  TBinaryProtocol(transport);
  			BankService.Client client = BankService.Client(protocol);
  			PrintWriter writer = new PrintWriter("clientLog.txt", "UTF-8");
  			
  			//newAccountRequest(client,writer);
			for (int i = 0; i < 100; i++)
				bc.newAccountRequest(client,writer);

			for (int i = 0; i < 100; i++) {
				ArrayList<Integer> list = bc.getAccountList();
				int accid = list.get(i);
				bc.depositRequest(client, writer, accid, 100);
			}

			int sum = 0;
			//Getting balance of 100 accounts and summing
			for (int i = 0; i < 100; i++) {
				ArrayList<Integer> list = bc.getAccountList();
				int accid = list.get(i);
				int bal = bc.balanceRequest(client, writer, accid);

				if (bal == -1)
					bal = 0;

				sum += bal;
			}

			System.out.println("Sum of balances = " + sum);
			writer.println("Sum of balances = " + sum);
			writer.println();			

			for (int i = 0; i < threadCount; i++) {

				new Thread () {
					public void run () {
						int a = rand.nextInt(bc.getAccountList().size());
						int b = rand.nextInt(bc.getAccountList().size());

						try {
						  		TTransport transport;
								transport = new TSocket(hostname, portname);
					  			transport.open();

					  			TProtocol protocol = new  TBinaryProtocol(transport);
					  			BankService.Client client = BankService.Client(protocol);

							for (int j = 0; j < iterationCount; j++) {
								ArrayList<Integer> arr = bc.getAccountList();
								bc.transferRequest (client, writer, arr.get(a), arr.get(b), 10);	
							}

						transport.close();

						} catch (IOException e) {
							// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			for (int i = 0; i < 100; i++) {
				ArrayList<Integer> list = bc.getAccountList();
				int accid = list.get(i);
				int bal = bc.balanceRequest(client, writer, accid);

				if (bal == -1)
					bal = 0;

				sum += bal;
			}

			System.out.println("Sum of balances = " + sum);
			writer.println("Sum of balances = " + sum);
			writer.println();
			transport.close();			
			writer.close();
	
	  }catch(TException e){
	  	e.printStackTrace();
	  }
	  
      

	}
}