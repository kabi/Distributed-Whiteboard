package whiteboard.object.style;

import java.awt.BasicStroke;
import java.awt.Color;

public class Pen {
	
  	private Color _penColor;
	private BasicStroke _pen;

	public Pen() {
        _pen = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        set_penColor(Color.BLACK);
    }//endconstructor

    
    //=========================================================setPen
    public void setPen(float pWidth, int pCap, int pJoin) {
        //--- Provided so users can set the style of pen, specifically the width
        _pen = new BasicStroke(pWidth, pCap, pJoin);
        
    }//end setPen
    
    
    
  //=========================================================getPen
    public BasicStroke getPen() {
        //--- Provided so we can access the style of pen currently set
        return _pen;
        
    }//end setPen

	public Color get_penColor() {
		return _penColor;
	}

	public void set_penColor(Color _penColor) {
		this._penColor = _penColor;
	}

}
