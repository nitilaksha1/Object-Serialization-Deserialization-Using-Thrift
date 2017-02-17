package requesttypes;
import java.io.Serializable;
import requesttypes.Request;

public class NewAccountRequest extends Request {

	private static final long serialVersionUID = 1L;
	
	public NewAccountRequest(String name) {
		super(name);
	}
}