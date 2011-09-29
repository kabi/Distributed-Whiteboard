package whiteboard.object;

import java.io.Serializable;

public class Vector implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6904823546426403044L;
	//TODO int between 0 and 600
	private final double x;
	private final double y;
	
	public final static Vector ORIGIN = new Vector(0,0,true);
	
	/**
	 * 
	 * @param coord1
	 * @param coord2
	 * @param cartesian <br />
	 * <li>If true, coordinates are cartesian</li>
	 * <li>If false, coordinates are polar</li> 
	 */
	public Vector(double coord1, double coord2, boolean cartesian) {
		if(cartesian) {
			this.x = coord1;
			this.y = coord2;
		} else {
			//polar coordinates
			this.x = coord1*Math.cos(coord2);
			this.y = coord1*Math.sin(coord2);
		}
	}
	
	/**
	 * Copy constructor
	 * @param pos
	 */
	public Vector(Vector pos) {
		this.x = pos.x;
		this.y = pos.y;
	}
	/**
	 * get the x coordinate
	 * @return
	 */
	public final double x() {
		return this.x;
	}
	/**
	 * @return
	 */
	public final double y() {
		return this.y;
	}
	
	/**
	 * The magnitude of the vector.
	 * @return
	 */
	public final double size() {
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * The angle the vector makes with the positive x axis.
	 * @return
	 */
	public final double azimuth() {
		return Math.atan2(y, x);
	}
	
	/**
	 * Add vec2 to vec1
	 */
	public static final Vector add(Vector vec1,Vector vec2) {
		return new Vector(vec1.x() + vec2.x(), vec1.y() + vec2.y(), true);
	}
	/**
	 * Subtract vec2 from vec1
	 */
	public static final Vector subtract(Vector vec1, Vector vec2) {
		return new Vector(vec1.x() - vec2.x(), vec1.y() - vec2.y(), true);
	}
	/**
	 * Rotate vec around the origin by angle
	 * @param vec The vector to be rotated
	 * @param angle The angle to rotate the vector
	 */
	public static final Vector rotate(Vector vec, double angle) {
		return new Vector(vec.size(), vec.azimuth() + angle, false);
	}
	/**
	 * The dot product of vec1 and vec2
	 */
	public static final double dot(Vector vec1, Vector vec2) {
		return vec1.x() * vec2.x() + vec1.y() * vec2.y();
	}
	/**
	 * Multiply a vector by a scalar
	 * @param vec
	 * @param scalar
	 * @return
	 */
	public static final Vector scale(Vector vec, double scalar) {
		return new Vector(vec.x() * scalar, vec.y() * scalar, true);
	}
	/**
	 * Retrieves the vector to the point halfway between two vectors.
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	public static final Vector midpoint(Vector vec1, Vector vec2) {
		return new Vector((vec1.x() + vec2.x())/2, (vec1.y() + vec2.y())/2, true);
	}
}
