package responsetypes;
import java.io.Serializable;
import responsetypes.Response;

enum Status {
    OK, FAILED
}

public class TransferResponse extends Response {
	private Status stat;

	public TransferResponse (String name, Status stat) {
		super(name);
		this.stat = stat;
	}

	public Status getResponse() {
		return stat;
	}
}
