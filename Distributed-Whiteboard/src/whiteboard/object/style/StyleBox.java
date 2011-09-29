package whiteboard.object.style;


//TODO THIS IS SHIT

public class StyleBox extends Style {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 541598083276416222L;
	private boolean filled;
	private int fillcolour = 0;
	
	public StyleBox(Brush b) {
		super(b);
		this.filled = false;
	}
	public StyleBox(Brush b, boolean filled, int fillcolour) {
		super(b);
		this.filled = filled;
		this.fillcolour = fillcolour;
	}
	
	public boolean isFilled() {
		return filled;
	}
	
	public int getFillColour() {
		return fillcolour;
	}
	
	public void setFill(boolean filled, int fillcolour) {
		this.filled = filled;
		this.fillcolour = fillcolour;
	}
	
	//TODO Possibly?!?
	 //==========================================setMode
  	public void setMode(boolean fill) {
  		//----Alternate between filled shapes and empty modes
  		_filledMode  = fill;
  		
  	}//end setMode

}
