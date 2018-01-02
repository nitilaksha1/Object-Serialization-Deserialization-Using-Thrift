package requesttypes;
import java.io.Serializable;
import requesttypes.Request;

public class DepositRequest extends Request {
	private int accountUID;
	private int amount;

	public DepositRequest (String name, int accUID, int amt) {

		super(name);

		this.accountUID = accUID;

		if (amt < 0)
			this.amount = 0;
		else
			this.amount = amt;
	}

	public int getAccUID () {
		return accountUID;
	}

	public int getAmount() {
		return amount;
	}
}
