package whiteboard.object.style;

public class Brush {
	public final int thickness;
	public final double blur;
	public final int spacing;
	public final int colour;
	
	public Brush(int thickness, double blur, int spacing, int colour){
		this.thickness = thickness;
		this.blur = blur;
		this.spacing = spacing;
		this.colour = colour;
	}
	
	public Brush(Brush brush) {
		this.thickness = brush.thickness;
		this.blur = brush.blur;
		this.spacing = brush.spacing;
		this.colour = brush.colour;
	}
}