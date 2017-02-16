package requesttypes;
import java.io.Serializable;

public class Request implements Serializable {
	private String reqName;

	public Request (String name) {
		reqName = name;
	}

	public String getReqName() {
		return reqName;
	}
}
