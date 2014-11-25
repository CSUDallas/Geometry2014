package geometry;


public class Point3D {
   public double x, y, z;		
   
   public Point3D(double xx, double yy, double zz){
	   x = xx;
	   y = yy;
	   z = zz;
   }
   
   public Point3D(double[] coords){
	   x = coords[0];
	   y = coords[1];
	   z = coords[2];
   }

   /**
    * Creates a Point3D from a Point3D.
    * @param point A Point3D to clone.
    */
   public Point3D(Point3D point) {
	   this.x = point.x;
	   this.y = point.y;
	   this.z = point.z;
   }

   double[] getCoords(){
	   double[] rv = new double[4];
	   rv[0] = x;
	   rv[1] = y;
	   rv[2] = z;
	   rv[3] = 1;
	   return rv;
   }
   
   public Point3D add(Point3D p){
	   return new Point3D(x + p.x, y + p.y, z + p.z);
   }
   
   public Point3D multAndAdd(double factor, Point3D p){
	   return new Point3D(x + factor*p.x, y + factor*p.y, z + factor*p.z);
   }
   
   public String toString(){
	   return String.format("(%.0f,  %.0f, %.0f)", x, y, z);
   }
   
   public boolean equals(Point3D p){
	   return x == p.x && y == p.y && z == p.z;
   }
   
   /**
    * Computes the distance between this point and another point.
   	* @param pt The other point to consider.
   	* @return The distance between this point and pt.
    */
   public double dist(Point3D pt) {
	   return Math.sqrt(distSq(pt));
   }

   	/**
   	 * Computes the square of the distance between two points.
   	 * This is faster than computing the actual distance.
   	 * @param pt The other point to consider.
   	 * @return The distance between this point and pt, squared.
   	 */
	public double distSq(Point3D pt) {
		double x1 = x - pt.x;
		double y1 = y - pt.y;
		double z1 = z - pt.z;
		return x1*x1 + y1*y1 + z1*z1;
	}

	/**
	 * Returns a new Point3D that is the difference between this Point3D and p.
	 * @param p The point to subtract from this point.
	 * @return The difference between the two points.
	 */
	public Point3D subtract(Point3D p) {
		return new Point3D(x - p.x, y - p.y, z - p.z);
	}

	/**
	 * Divides this Point3D by the specified value.
	 * @param divisor The number by which to divide this point.
	 * @return A new Point3D representing this / divisor.
	 */
	public Point3D divide(double divisor) {
		return new Point3D(x / divisor, y / divisor, z / divisor);
	}

}
