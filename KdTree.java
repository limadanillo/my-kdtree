	
public class KdTree{
	private int c;
	private static class Node {
		private Point2D 	p;
		private RectHV  	rect;
		private Node    	lt;
		private Node    	rt;
		private boolean 	v;
		public Node(Point2D p, boolean v) {
			this.p = p;
			this.v = v;
		}
		public Node() { }
	}
    
	private Node root;
	/**
	 * Initialize a empty set to store points
	 */
	public KdTree() {
		// construct an empty set of points
		root      = new Node();
		root.rect = new RectHV(0,0,1,1);
		root.v    = true;
		c         = 0;
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
		// number of points in the set//		KdTree test = new KdTree();
//		Point2D p1 = new Point2D(0.5,0.2);
//		Point2D p2 = new Point2D(0.3,0.4);
//		Point2D p3 = new Point2D(0.6,0.2);
//		Point2D p4 = new Point2D(0.4,0.9);
//		test.insert(p1);
//		test.insert(p2);
//		test.insert(p3);
//		test.insert(p4);
//		test.draw();
//		StdOut.println(test.size());
		return this.c;
	}
	
	// add the point p to the set (if it is not already in the set)
	public void insert(Point2D p) {
		if (p == null) throw new NullPointerException("call insert() with a null point");
		if (!this.contains(p)) {
			put(root, p, root.v);
//			if (node.v && node.rt != null)
//				node.rt.rect = new RectHV(node.p.x(),node.rect.ymin(),node.rect.xmax())
		}
	}
	
	private Node put(Node n, Point2D p, boolean vertical) {
		if (n == null) {return new Node(p, vertical);}
		if (n.p == null) { n.p = p; this.c ++ ;}
		else {
			if ((n.v && n.p.x() > p.x()) || (!n.v && n.p.y() > p.y())) {
				n.lt = put(n.lt, p, n.v);
				n.lt.v = !n.v;
				n.lt.rect = leftRect(n);
//				n.rt.rect = new RectHV(n.p.x(),n.rect.ymin(),n.rect.xmax(),n.rect.ymax());
			}
			else {
				n.rt = put(n.rt, p, n.v);
				n.rt.v = !n.v;
				n.rt.rect = rightRect(n);
//				n.rt.rect = new RectHV(n.rect.xmin(),n.p.y(),n.rect.xmax(),n.rect.ymax());
			}
		}
		return n;
	}
	
	/**
	 * Check whether point <tt> p <tt/> is contained by the set
	 * @param p t he point required to check
	 * @return true if included, false otherwise.
	 */
	public boolean contains(Point2D p) {
		// does the set contain the point p?
		if (p == null) throw new NullPointerException("call contains() with a null point");
		if (this.isEmpty()) return false;
		return get(root, p);
	}
	
	private boolean get(Node n, Point2D p) {
		if (n == null) return false;
		if (n.p.equals(p)) return true;
		else if ((n.v && n.p.x() > p.x()) || (!n.v && n.p.y() > p.y())) return get(n.lt, p);
		else return get(n.rt, p);
	}
	
    public void draw() {
    	Queue<Node> qsV = new Queue<Node>();
    	Queue<Node> qsH = new Queue<Node>();
    	qsV.enqueue(root);
//    	boolean x = true;
    	Node n;
    	Point2D pStart,pEnd;
    	while (!qsV.isEmpty() || !qsH.isEmpty()) {
    		if (!qsV.isEmpty()) {
    			n = qsV.dequeue();
    			if (n.lt != null) qsH.enqueue(n.lt);
    			if (n.rt != null) qsH.enqueue(n.rt);
    			StdDraw.setPenRadius(0.01);
    			StdDraw.setPenColor(StdDraw.BLACK);
    			n.p.draw();
//    			StdOut.println(n.p.toString()); /////// TODO
    			StdDraw.setPenRadius();
    			StdDraw.setPenColor(StdDraw.RED);
    			pStart = new Point2D(n.p.x(), n.rect.ymin());
    			pEnd   = new Point2D(n.p.x(), n.rect.ymax());
    			pStart.drawTo(pEnd);
    		}
    		if(!qsH.isEmpty()) {
    			n = qsH.dequeue();
       			if (n.lt != null) qsV.enqueue(n.lt);
    			if (n.rt != null) qsV.enqueue(n.rt);
    			StdDraw.setPenRadius(0.01);
    			StdDraw.setPenColor(StdDraw.BLACK);
    			n.p.draw();
//    			StdOut.println(n.p.toString()); ///// TODO
    			StdDraw.setPenRadius();
    			StdDraw.setPenColor(StdDraw.BLUE);
    			pStart = new Point2D(n.rect.xmin(), n.p.y());
    			pEnd   = new Point2D(n.rect.xmax(), n.p.y());
    			pStart.drawTo(pEnd);
        		}
    	}
    	StdDraw.show(0);
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	if (rect == null) throw new NullPointerException("call range() with a null rectangle");
    	SET<Point2D> ps = new SET<Point2D>();
    	Queue<Node> qs = new Queue<Node>();
    	qs.enqueue(root);
    	Node n = new Node();
    	while (!qs.isEmpty()) {
    		n = qs.dequeue();
    		if (n.rect.intersects(rect)) { 
    			if (n.lt != null && rect.intersects(n.lt.rect)) qs.enqueue(n.lt);
    			if (n.rt != null && rect.intersects(n.rt.rect)) qs.enqueue(n.rt);
    		}
    		if (rect.contains(n.p)) ps.add(n.p);
    	}
    	return ps;
    }
    
    private RectHV leftRect(Node node) {
    	if (node.v)
    		return new RectHV(node.rect.xmin(),node.rect.ymin(),node.p.x(),node.rect.ymax());
    	else
    		return new RectHV(node.rect.xmin(),node.rect.xmax(),node.rect.ymin(),node.p.y());
    }
    
    private RectHV rightRect(Node node) {
    	if (node.v)
    		return new RectHV(node.p.x(),node.rect.ymin(),node.rect.xmax(),node.rect.ymax());
    	else
    		return new RectHV(node.rect.xmin(),node.rect.xmax(),node.p.y(),node.rect.ymax());
    }

    public Point2D nearest(Point2D p) {
    	if (p == null) throw new NullPointerException("call nearest() with a null point");
    	double distance = Double.POSITIVE_INFINITY;
    	Queue<Node> qs = new Queue<Node>(); 
    	Node minNode = new Node();
    	qs.enqueue(root);
    	Node n;
    	while (!qs.isEmpty()) {
    		n = qs.dequeue();
    		if (n.lt != null) qs.enqueue(n.lt);
			if (n.rt != null) qs.enqueue(n.rt);
    		if ( n.p.distanceSquaredTo(p) < distance) { 
    			distance = n.p.distanceSquaredTo(p);
    			minNode = n;
    		}
    	}
    	return minNode.p;
    }
    
	public static void main(String[] args) {
		KdTree test = new KdTree();
		Point2D p1 = new Point2D(0.5,0.2);
		Point2D p2 = new Point2D(0.3,0.4);
		Point2D p3 = new Point2D(0.6,0.2);
		Point2D p4 = new Point2D(0.4,0.9);
		test.insert(p1);
		test.insert(p2);
		test.insert(p3);
		test.insert(p4);
		test.draw();
		StdOut.println(test.size());
		RectHV tt = new RectHV(0,0,1.0,1.0);
		for (Point2D i:test.range(tt))
			StdOut.println(i.toString());
//        String filename = args[0];
//        In in = new In(filename);
//
//
//        StdDraw.show(0);
//
//        // initialize the data structures with N points from standard input
//        PointSET brute = new PointSET();
//        KdTree kdtree = new KdTree();
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            Point2D p = new Point2D(x, y);
//            kdtree.insert(p);
//            brute.insert(p);
//        }
//        StdOut.println(kdtree.size());
////        StdOut.println(kdtree.size());
////        Point2D p = new Point2D(0.975528, 0.654508);
////        StdOut.println(kdtree.contains(p));
////        StdOut.println(kdtree.nearest(p).toString());
////        StdOut.println(kdtree.nearest(p).distanceTo(p));
//        for (Point2D s:kdtree.range(tt))
//        	StdOut.println(s.toString());
////        kdtree.draw();
	}
}