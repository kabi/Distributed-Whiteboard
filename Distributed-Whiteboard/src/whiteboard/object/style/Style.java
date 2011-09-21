package whiteboard.object.style;

import java.io.Serializable;

public abstract class Style 
	implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2812094884404957606L;
	protected Brush brush;
	
	public Style(Brush b) {
		this.brush = b;
	}
	
	public Brush getBrush() {
		return brush;
	}
	
	public void setBrush(Brush brush) {
		this.brush = brush;
	}

}
