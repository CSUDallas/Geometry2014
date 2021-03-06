package geometry;

import java.awt.Color;


/**
 * Holds points of a triangle in 3D.
 * The points are always in counter-clockwise order
 * as seen from the "outside" of the triangle's object, 
 * if it has an outside, and an object.
 */

public class Triangle3D implements Comparable<Triangle3D>{
	public Point3D[] points;		// Vertices of the triangle
	public Triangle3D[] triangles; // Neighbor triangles. triangles[0] is on edge 0-1.
	public static Point3D cameraPos; // for use with our compareTo method
	//public static Color c = new Color(1,1,1,1)
	
	public Color triColor = Color.white;
	/**
	 * Constructor
	 * Constructs a Triangle3D having the three given points as vertices
	 * @param p0  first vertex
	 * @param p1  second vertex
	 * @param p2  third vertex, going counter-clockwise around the triangle as seen from the "outside"
	 */
	public Triangle3D(Point3D p0, Point3D p1, Point3D p2){
		points = new Point3D[3];
		points[0] = p0;
		points[1] = p1;
		points[2] = p2;
	}

	/**
	 * Creates a new Triangle3D from the triangle provided.
	 * @param source A Triangle3D to duplicate
	 */
	public Triangle3D(Triangle3D source) {
		this(source.points[0], source.points[1],
				source.points[2], source.triColor);
	}

	public Triangle3D(Point3D p0, Point3D p1, Point3D p2, Color triColor){
		this(p0,p1,p2);
		this.triColor = triColor;
	}
	
	/**
	 * Prints a triple of the triangle's points.
	 */
	public String toString(){
		return "Triangle: (" + points[0].toString() + ", " + 
				points[1].toString() + ", " + points[2].toString() + ")";
	}
	
	/**
	 * Translates the triangle by adding point d to all three vertices
	 * @param d  the displacement vector
	 * @return
	 */
	public Triangle3D add(Point3D d){
		return new Triangle3D(points[0].add(d), points[1].add(d), points[2].add(d),this.triColor);
	}
	
	public boolean hasVertex(Point3D p){
		return points[0].equals(p) || points[1].equals(p) || points[2].equals(p);
	}

	@Override
	public int compareTo(Triangle3D o) {
		Double myDist = (points[0].x - cameraPos.x)*(points[0].x - cameraPos.x) +
				(points[0].y - cameraPos.y)*(points[0].y - cameraPos.y) +
				(points[0].z - cameraPos.z)*(points[0].z - cameraPos.z);
		Double otherDist = (o.points[0].x - cameraPos.x)*(o.points[0].x - cameraPos.x) +
				(o.points[0].y - cameraPos.y)*(o.points[0].y - cameraPos.y) +
				(o.points[0].z - cameraPos.z)*(o.points[0].z - cameraPos.z);
		return otherDist.compareTo(myDist);
	}
}
