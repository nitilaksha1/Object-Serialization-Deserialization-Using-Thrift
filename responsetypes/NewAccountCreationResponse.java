package responsetypes;
import java.io.Serializable;
import responsetypes.Response;

public class NewAccountCreationResponse extends Response {
	private static final long serialVersionUID = 1L;
	private int UID;

	public NewAccountCreationResponse (String name, int UID) {
		super(name);
		this.UID = UID;
	}

	public int getResponse() {
		return UID;
	}
}
