package responsetypes;
import java.io.Serializable;
import responsetypes.Response;

public class BalanceResponse extends Response {
	private int currBal;

	public BalanceResponse (String name, int currBal) {
		super(name);
		this.currBal = currBal;
	}

	public int getResponse() {
		return currBal;
	}
}
