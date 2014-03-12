/***************************************************************************
 * Compile: javac PointSET.java
 * Execute: java PointSET.java inputfile
 * Dependencies: Point2D, SET, RectHV
 * 
 * Implementing Brute-force Implementation of searing points in a rectangle
 * range.
 ***************************************************************************/

/**
 * The class <tt> PointSET <tt/> is an immutable data type to represent a set of
 * points in a unit box.
 * @author Di Liu
 */

public class PointSET {
	
	private SET<Point2D> points;
    
	/**
	 * Initialize a empty set to store points
	 */
	public PointSET() {
		// construct an empty set of points
		points = new SET<Point2D>();
	}
	
	/**
	 * Check whether the set is empty
	 * @return true if the  set is not empty, false otherwise.
	 */
	public boolean isEmpty() {
		// is the set empty?
		return points.isEmpty();
	}
	
	/**
	 * Get the size of the point set
	 * @return how many points is included in this set
	 */
	public int size() {
		// number of points in the set
		return points.size();
	}
	
	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) throw new NullPointerException("call insert() with a null point");
		if (!points.contains(p)) {
			points.add(p);
		}
	}
	
	/**
	 * Check whether point <tt> p <tt/> is contained by the set
	 * @param p the point required to check
	 * @return true if included, false otherwise.
	 */
	public boolean contains(Point2D p) {
		// does the set contain the point p?
		if (p == null) throw new NullPointerException("call contains() with a null point");
		return points.contains(p);
	}
	
    public void draw() {
    	// draw all of the points to standard draw
    	for (Point2D p: points)
    		p.draw();
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	if (rect == null) throw new NullPointerException("call range() with a null rectangle");
    	SET<Point2D> ps = new SET<Point2D>();
    	for (Point2D p:points) {
    		if (rect.contains(p))
    			ps.add(p);
    	}
    	return ps;
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	if (p == null) throw new NullPointerException("call nearest() with a null point");
    	double distance = 0.0;
    	Point2D nearP = new Point2D(0,0);
    	for (Point2D n:points) {
    		if (p.distanceTo(n) < distance)
    			distance = p.distanceTo(n);
    			nearP = n;
    	}
    	return nearP;
    }
    
}
