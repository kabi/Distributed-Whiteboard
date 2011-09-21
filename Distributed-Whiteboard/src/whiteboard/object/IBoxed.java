package whiteboard.object;

/**
 * The interface which all objects which are bounded by a rectangle must implement.
 * @author ovangle
 *
 */
public interface IBoxed {
	/**
	 * Scale object, preserving the centre of the object.
	 * Preserves aspect ratio.
	 * @param percentage  
	 */
	public abstract void scale(double percentage) throws ZeroSizeObjectException;
	
	/**
	 * Rotate the object by angle degrees clockwise, preserving the centre of the object
	 * @param angle
	 */
	public abstract void rotate(double angle);
	
	/**
	 * Rotate the object by angle degrees clockwise, preserving the point pos.
	 * @param angle
	 * @param p
	 */
	public abstract void rotateAround(double angle, Vector pos);
	
	/**
	 * Retrieve the rectangle which bounds the object
	 * @return
	 */
	public abstract Rectangle getBox();
	
	
	/**
	 * Resize the point from one of the nine resize points attached to the object
	 * @param resize_to The position the resize point was dragged to
	 * @param resize_point
	 * The active resize point. Can take one of eight values:<br />
	 * 		    <li> "middleleft" - stretches the object to the left. Does not preserve aspect ratio </li>
	 * 			<li> "middleright" - stretches the object to the right. Does not preserve aspect ratio </li>
	 * 			<li> "middleupper" - stretches the object upwards. Does not preserve aspect ratio</li>
	 * 			<li> "middlelower" - stretches the object downwards. Does not preserve aspect ratio</li>
	 * 			<li> "lowerleft" - stretches the object down and to the left. Preserves aspect ratio</li>
	 * 			<li> "lowerright" - stretches the object down and to the right. preserves aspect ratio</li>
	 * 			<li> "upperleft" - stretches the object up and to the left. Preserves aspect ratio</li>
	 * 			<li> "upperright" - stretches the object up and to the right. Preserves aspect ratio</li>
	 * 	@return True if resized correctly.
	 *  @throws ZeroSizeObjectException If the object is resized to zero size
	 *  @throws UnsupportedOperationException If the object has a fixed aspect ratio.
	 *  	This exception should be handled by replacing the object with a superclass with no fixed aspect ratio 
	 *  	(eg. A square should be replaced with a rectangle, a circle with an oval etc).
	 */
	public abstract boolean resize(Vector resize_to, String resize_point)
		throws ZeroSizeObjectException, UnsupportedOperationException;

}
