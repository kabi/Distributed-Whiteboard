package whiteboard.object;
import java.io.Serializable;

import whiteboard.object.style.*;

/**
 * <b> ABoxed</b> <br />
 * The default abstract implementation of the IBoxed interface.
 * @author ovangle
 *
 */
public abstract class ABoxed extends AWhiteboardObject 
	implements IBoxed, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8556153477535623672L;
	//The position of the bottom left corner, relative to the position of the box
	protected Vector rel_bottomleft;
	//The position of the top right corner, relative to the position of the box
	protected Vector rel_bottomright;
	protected StyleBox style;
	
	public ABoxed(Vector position) {
		super(position);
	}
	
	/**
	 * ABoxed constructor. 
	 * Constructs a box between two points, with the position vector set to its centre
	 * and the startbox and endbox 
	 * @param startbox A vector relative to the origin
	 * @param endbox A vector relative to the origin
	 * @throws ZeroSizeObjectException
	 */
	public ABoxed(Vector startbox, Vector endbox) throws ZeroSizeObjectException {
		super(Vector.midpoint(startbox,endbox));
		if(startbox.x() == endbox.x() || startbox.y() == endbox.y()) {
			throw new ZeroSizeObjectException();
		} else {
			if(startbox.x() < endbox.x() && startbox.y() < endbox.y()){
				//startbox in bottom left, endbox in top right
				this.rel_bottomleft = Vector.subtract(startbox, this.pos);
				this.rel_bottomright = Vector.subtract(new Vector(endbox.x(),startbox.y(),true), this.pos);
			} else if(startbox.x() < endbox.x() && startbox.y() > endbox.y()) {
				//startbox in top left, endbox in bottom right
				this.rel_bottomleft = Vector.subtract(new Vector(startbox.x(), endbox.y(), true), this.pos);
				this.rel_bottomright = Vector.subtract(endbox, this.pos);
			} else if(startbox.x() > endbox.x() && startbox.y() < endbox.y()) {
				//startbox in bottom right, endbox in top left
				this.rel_bottomleft = Vector.subtract(new Vector(endbox.x(),startbox.y(),true), this.pos);
				this.rel_bottomright = Vector.subtract(startbox, this.pos);
			} else {
				//startbox in top right, endbox in bottom left.
				this.rel_bottomleft = Vector.subtract(endbox,this.pos);
				this.rel_bottomright = Vector.subtract(new Vector(startbox.x(),endbox.y(),true), this.pos);
			}
		}
	}
	
	public Style getStyle() {
		return style;
	}
	public void setStyle(Style newstyle) {
		if(newstyle instanceof StyleBox) {
			this.style = (StyleBox) newstyle;
		} else {
			this.style = new StyleBox(newstyle.getBrush()); 
		}
	}
	
	//TOM: TODO:
	/*
	 * (non-Javadoc)
	 * @see whiteboard.object.IBoxed#rotate(double)
	 * 
	 * public int width()
	 * public int height()
	 */
	

	@Override
	public void rotate(double angle) {
		Vector.rotate(this.rel_bottomleft, angle);
		Vector.rotate(this.rel_bottomright, angle);
		
	}
	
	@Override
	public void rotateAround(double angle, Vector p) {
		//TODO test method
		Vector posprime = Vector.rotate(Vector.subtract(this.pos,p), angle);
		this.move(posprime);
		Vector.rotate(rel_bottomleft, angle);
		Vector.rotate(rel_bottomright, angle);	
	}

	@Override
	public void scale(double percentage) throws ZeroSizeObjectException {
		Vector.scale(rel_bottomleft, Math.sqrt(percentage));
		Vector.scale(rel_bottomright, Math.sqrt(percentage));
		
	}
	
	@Override
	public boolean resize(Vector resizeTo, String resizePoint)
			throws ZeroSizeObjectException, UnsupportedOperationException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public abstract Rectangle getBox();

}
