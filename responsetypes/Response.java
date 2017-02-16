package responsetypes;
import java.io.Serializable;

public class Response implements Serializable {
	private String reqName;

	public Response (String name) {
		reqName = name;
	}

	public String getReqName() {
		return reqName;
	}
}
