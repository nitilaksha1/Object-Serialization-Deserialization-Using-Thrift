package responsetypes;
import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	private String reqName;

	public Response (String name) {
		reqName = name;
	}
	
	public String getReqName() {
		return reqName;
	}
}
