package whiteboard.object;
/**
 * <b>Zero Size Exception</b><br />
 * <p>
 * An exception thrown when a Whiteboard Object is either<br />
 * created with zero area or length or is resized to be of <br />
 * zero area or length. 
 * </p>
 * <p>
 * In general, should be handled by removing the object from <br />
 * the list of whiteboard objects </p>
 * @author ovangle
 * @version 0.1
 *
 */
public class ZeroSizeObjectException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2127202267208004560L;

	public ZeroSizeObjectException() {
		super();
	}
	public ZeroSizeObjectException(String message) {
		super(message);
	}
	public ZeroSizeObjectException(String message, Throwable cause) {
		super(message,cause);
	}
	public ZeroSizeObjectException(Throwable cause) {
		super(cause);
	}
	

}
