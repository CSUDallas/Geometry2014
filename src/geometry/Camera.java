package geometry;

/**
 * Simple structure to represent a camera.
 * Consists of a location and a direction vector.
 */
public class Camera {

	public Point3D position, direction;

	public Camera(Point3D position, Point3D direction) {
		this.position  = position;
		this.direction = direction;
	}

}
