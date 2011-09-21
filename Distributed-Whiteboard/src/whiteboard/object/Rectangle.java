package whiteboard.object;

import java.io.Serializable;

public class Rectangle extends ABoxed 
	implements IBoxed, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6954229678873898058L;

	public Rectangle(Vector startbox, Vector endbox) throws ZeroSizeObjectException{
		super(startbox, endbox);
	}
	
	public Rectangle(Rectangle rect) throws ZeroSizeObjectException {
		super(rect.getPosition());
		this.rel_bottomleft = Vector.subtract(rect.bottomLeft(), rect.getPosition());
		this.rel_bottomright = Vector.subtract(rect.bottomRight(), rect.getPosition());
	}
	
	public Vector bottomLeft() {
		return Vector.add(this.rel_bottomleft, this.pos);
	}
	public Vector bottomRight() {
		return Vector.add(this.rel_bottomright, this.pos);
	}
	public Vector topRight() {
		return Vector.subtract(this.pos, this.rel_bottomleft);
	}
	public Vector topLeft() {
		return Vector.subtract(this.pos, this.rel_bottomright);
	}
	public Vector middleLeft() {
		return Vector.midpoint(this.bottomLeft(), this.topLeft());
	}
	public Vector middleRight() {
		return Vector.midpoint(this.bottomRight(), this.topRight());
	}
	public Vector middleBottom() {
		return Vector.midpoint(this.bottomLeft(), this.bottomRight());
	}
	public Vector middleUpper() {
		return Vector.midpoint(this.topRight(), this.topLeft());
	}
	

	@Override
	public Rectangle getBox() {
		return this;
	}


}
