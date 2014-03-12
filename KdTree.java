	
public class KdTree{
	private int c;
	private static class Node {
		private Point2D p;
		private RectHV  rect;
		private Node    lt;
		private Node    rt;
	}
    
	private Node root;
	/**
	 * Initialize a empty set to store points
	 */
	public KdTree() {
		// construct an empty set of points
		root = new Node();
		c    = 0;
	}
	
	/**
	 * Check whether the set is empty
	 * @return true if the  set is not empty, false otherwise.
	 */
	public boolean isEmpty() {
		// is the set empty?
		return root.p == null;
	}
	
	/**
	 * Get the size of the point set
	 * @return how many points is included in this set
	 */
	public int size() {
		// number of points in the set
		return this.c;
	}
	
	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) throw new NullPointerException("call insert() with a null point");
		boolean direction = false; // false means comparing x, while true means comparing y
		Node     node      = new Node();
		if (this.isEmpty()) root.p = p;
		else {
			if (direction) 
			
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