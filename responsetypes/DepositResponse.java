package responsetypes;
import java.io.Serializable;
import responsetypes.Response;

enum Status {
    OK, FAILED
}

public class DepositResponse extends Response {
	private String stat;

	public DepositResponse (String name, String stat) {
		super(name);
		this.stat = stat;
	}

	public String getResponse() {
		return stat;
	}
}
