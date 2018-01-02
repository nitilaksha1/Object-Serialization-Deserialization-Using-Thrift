package requesttypes;
import java.io.Serializable;

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	private String reqName;

	public Request (String name) {
		reqName = name;
	}

	public String getReqName() {
		return reqName;
	}
}
