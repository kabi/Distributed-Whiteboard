package whiteboard.object.style;

//TODO: THIS IS SHIT

public class StyleLine 
	extends Style {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3798795516107334168L;
	private boolean eraser;
	
	public StyleLine(Brush brush) {
		super(brush);
		this.eraser = false;
	}
	
	public StyleLine(Brush brush, boolean eraser) {
		super(brush);
		this.eraser = eraser;
	}
	
	public StyleLine(StyleLine style) {
		super(style.getBrush());
		this.eraser = style.eraser;
	}
	
	public Brush getBrush() {
		return brush;
	}
	
	public boolean isEraser() {
		return eraser;
	}	
}
