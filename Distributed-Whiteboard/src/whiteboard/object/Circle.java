package whiteboard.object;

public class Circle extends ABoxed{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8578326420221166836L;

	public Circle(Vector startbox, Vector endbox) throws ZeroSizeObjectException {
		super(startbox, closestSquare(startbox, endbox));
	}
	
	/**
	 * Returns a vector to the top right corner of the closest square obtained when boxing
	 * from the position vec1 to the position vec2.
	 * @param vec1
	 * @param vec2
	 * @return
	 */
	private static Vector closestSquare(Vector vec1, Vector vec2) {
		//Find the azimuth of vec1 - vec2
		double azimuth = Vector.subtract(vec1,vec2).azimuth();
		double return_angle = 0;
		if(0 < azimuth && azimuth < Math.PI/2) {
			//azimuth in first quadrant, return vec1 + |vec2 - vec1|e^(i*PI/4)
			return_angle = Math.PI/4;
			
		} else if (Math.PI/2 < azimuth && azimuth < Math.PI) {
			//azimuth in second quadrant. Return vec1 + |vec2 - vec1 | * e^(3i*PI/4))
			return_angle = 3*Math.PI/4;
		} else if (-Math.PI < azimuth && azimuth < -Math.PI/2) {
			//azimuth in third quadrant. Return vec1 + |vec2 - vec1| * e(-3i*PI/4)
			return_angle = -3*Math.PI/4;
		} else if (-Math.PI/2 <azimuth && azimuth < 0) {
			//azimuth in third quadrant. Return vec1 + |vec2 - vec1| * e(-i*PI/4)
		} else {
			//azimuth lies along an axis. No non-zero sized square possible, 
			//so return vec1;
			return vec1;
		}
		return Vector.add(vec1,
				new Vector(Vector.subtract(vec2,vec1).size(),return_angle,false));
	}

	@Override
	public Rectangle getBox() {
		// TODO Auto-generated method stub
		return null;
	}
}
