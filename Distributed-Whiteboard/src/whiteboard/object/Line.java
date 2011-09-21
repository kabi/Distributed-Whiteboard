package whiteboard.object;

import java.io.Serializable;

/**
 * <b> Line Whiteboard Object </b> <br />
 * A straight line. What were you expecting?
 * @author ovangle
 * @version 0.1
 *
 */

public class Line extends AWhiteboardObject 
	implements Serializable {
	private static final long serialVersionUID = 6778327755739664932L;
	private Vector endpoint;
	
	public Line(Vector position, Vector endpoint) throws ZeroSizeObjectException {
		super(position);
		if(position.equals(endpoint)) {
			throw new ZeroSizeObjectException();
		}
		this.endpoint = endpoint;
	}
	
	public Vector getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(Vector endpoint) {
		this.endpoint = endpoint;
	}	
}
