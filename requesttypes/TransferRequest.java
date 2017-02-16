package requesttypes;
import java.io.Serializable;
import requesttypes.Request;

public class TransferRequest extends Request {
	private int sourceAccUID;
	private int targetAccUID;
	private int amount;

	public TransferRequest (String name, int srcAccUID, int targAccUID, int amt) {
		super(name);
		sourceAccUID = srcAccUID;
		targetAccUID = targAccUID;

		if (amt < 0)
			amount = 0;
		else
			amount = amt;
	}

	public int getSourceAccUID () {
		return sourceAccUID;
	}

	public int gettargetAccUID () {
		return targetAccUID;
	}

	public int getAmount () {
		return amount;
	}
}