package geometry;

import java.awt.Color;

public class EnvironmentObject implements Comparable<EnvironmentObject>{
	protected DSArrayList<Triangle3D> triangles;
	public  Point3D center;
	public  Point3D velocity;
	public static Point3D cameraPos; // For use with our compareTo method
	
	public EnvironmentObject(){
		triangles = new DSArrayList<Triangle3D>();
		center = new Point3D(0, 0, 0);
		velocity = new Point3D(0, 0, 0);
	}
	
	public static EnvironmentObject makeCube(double side, double cx, double cy, double cz){
		EnvironmentObject eo = EnvironmentObject.makeCube(side);
		eo.center = new Point3D(cx, cy, cz);
		eo.velocity = new Point3D(0, 0, 0);
		return eo;
	}
	
	public static EnvironmentObject makeCube(double side){
		EnvironmentObject eo = new EnvironmentObject();
		
		double r = side/2;
		Point3D p1 = new Point3D(-r, -r, -r);
		Point3D p2 = new Point3D(r, -r, -r);
		Point3D p3 = new Point3D(r, r, -r);
		Point3D p4 = new Point3D(-r, r, -r);
		Point3D p5 = new Point3D(-r, -r, r);
		Point3D p6 = new Point3D(r, -r, r);
		Point3D p7 = new Point3D(r, r, r);
		Point3D p8 = new Point3D(-r, r, r);
		Color c = Color.getHSBColor((float)Math.random(),(float)Math.random(),(float)Math.random());
		
		eo.triangles.add(new Triangle3D(p1, p4, p3,c));
		eo.triangles.add(new Triangle3D(p1, p3, p2,c));
		eo.triangles.add(new Triangle3D(p1, p2, p6,c));
		eo.triangles.add(new Triangle3D(p1, p6, p5,c));
		eo.triangles.add(new Triangle3D(p2, p3, p7,c));
		eo.triangles.add(new Triangle3D(p2, p7, p6,c));
		eo.triangles.add(new Triangle3D(p8, p5, p6,c));
		eo.triangles.add(new Triangle3D(p8, p6, p7,c));
		eo.triangles.add(new Triangle3D(p8, p7, p3,c));
		eo.triangles.add(new Triangle3D(p8, p3, p4,c));
		eo.triangles.add(new Triangle3D(p4, p1, p5,c));
		eo.triangles.add(new Triangle3D(p4, p5, p8,c));
		return eo;
	}
	
	public DSArrayList<Triangle3D> getTriangles(){
		DSArrayList<Triangle3D> rv = new DSArrayList<Triangle3D>(triangles.size());
		for(int i = 0; i < triangles.size(); i++){
			rv.add(triangles.get(i).add(center));
		
		}
		return rv;
	}

	@Override
	/**
	 * Compares this EnvironmentObject's distance to the camera with another one's distance.
	 * 
	 * @param o  The other EnvironmentObject
	 * 
	 * This method is included so that we can ask the Collections class to sort
	 * our EnvironmentObjects by distance from the camera.
	 */
	public int compareTo(EnvironmentObject o) {
		Double myDist = (center.x - cameraPos.x)*(center.x - cameraPos.x) +
				(center.y - cameraPos.y)*(center.y - cameraPos.y) +
				(center.z - cameraPos.z)*(center.z - cameraPos.z);
		Double otherDist = (o.center.x - cameraPos.x)*(o.center.x - cameraPos.x) +
				(o.center.y - cameraPos.y)*(o.center.y - cameraPos.y) +
				(o.center.z - cameraPos.z)*(o.center.z - cameraPos.z);
		return otherDist.compareTo(myDist);
	}
	
}
