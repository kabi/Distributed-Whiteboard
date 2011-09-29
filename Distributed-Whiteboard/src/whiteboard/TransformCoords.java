package whiteboard;


//TODO Unified coordinate system
/**
 * Transform screen coordinates
 *
 * @author Eric
 *
 */
public class TransformCoords {

    public static double userX(double x, int width) 
    { 
    	return 0 + x * (1 - 0) / width;    
    }
    
    public static double userY(double y, int height) 
    { 
    	return 1 - y * (1 - 0) / height;   
    }


}
