import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
public class BankServer {

	public static BankHandler handler;
	public static BankService.Processor processor;

  public static void main(String [] args) {
    try {
      handler = new BankHandler();
      processor = new BankService.Processor(handler);

      Runnable simple = new Runnable() {
        public void run() {
          someMethod(processor);
        }
      };      
     
      new Thread(simple).start();
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  public static void someMethod(BankService.Processor processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
      System.out.println("Starting the simple server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }