package requesttypes;
import java.io.Serializable;
import requesttypes.Request;

public class BalanceRequest extends Request {
	private int accountUID;

	public BalanceRequest (String name, int accUID) {
		super(name);
		accountUID = accUID;
	}

	public int getAccUID () {
		return accountUID;
	}
}