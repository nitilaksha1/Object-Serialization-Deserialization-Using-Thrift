package requesttypes;
import java.io.Serializable;
import requesttypes.Request;

public class NewAccountRequest extends Request {

	public NewAccountRequest(String name) {
		super(name);
	}
}