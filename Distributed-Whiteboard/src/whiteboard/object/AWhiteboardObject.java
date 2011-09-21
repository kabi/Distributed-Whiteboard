package whiteboard.object;
import whiteboard.object.style.*;

import java.io.Serializable;
/**
 * <b>Whiteboard Object</b> <br />
 * The superclass for all objects which can be drawn on the whiteboard
 * @author ovangle
 * @version 0.1
 */

public abstract class AWhiteboardObject implements Serializable {
	private static final long serialVersionUID = 7546812428284101414L;
	protected Vector pos;
	protected Style style;
	
	public AWhiteboardObject(Vector pos) {
		this.pos = pos;
	}
	
	public Vector getPosition() { return pos; }
	
	public void move(Vector pos) {
		this.pos = pos;
	}
	
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style newstyle) {
		this.style = newstyle;
	}
}
