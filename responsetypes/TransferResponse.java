package responsetypes;
import java.io.Serializable;
import responsetypes.Response;

public class TransferResponse extends Response {
	private String stat;

	public TransferResponse (String name, String stat) {
		super(name);
		this.stat = stat;
	}

	public String getResponse() {
		return stat;
	}
}
