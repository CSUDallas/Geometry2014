package geometry;

import java.awt.Dimension;
import java.awt.RenderingHints;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.Timer;

public class DelaunayArena extends Arena {
	static DelaunayArena arena;

	public static void main(String[] args) {
		// Set up the arena and an environment
		arena = new DelaunayArena();
		Environment e = new Environment();
		arena.e = e;
		arena.buildEnvironment();
		arena.keysDown = new HashSet<Character>();
		arena.setPreferredSize(new Dimension(e.WIDTH, e.HEIGHT));
		arena.rh = new RenderingHints(null);
		arena.rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Set up a JFrame to hold our arena (Jpanel)
		JFrame arenaFrame = new JFrame();
		arenaFrame.addKeyListener(arena);
		arenaFrame.add(arena);
		arenaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		arenaFrame.setLocation(500, 50);
		arenaFrame.pack();
		arenaFrame.setVisible(true);

		// Start the arena's run loop
		arena.timer = new Timer(30, arena.runLoop);
		arena.timer.start();
	}
	
	/**
	 * Creates an environment for 3D graphing
	 */
	private void buildEnvironment(){
		e = new Environment();

		// Build the floor
		int factor = 5;
		for(int i = -30; i <= 30; i++){
			for(int j = -30; j <= 30; j++){
				e.addTriangle(new Triangle3D(
						new Point3D(i*factor, j*factor, -10),
						new Point3D((i+1)*factor, j*factor, -10),
						new Point3D((i+1)*factor, (j+1)*factor, -10)));
				e.addTriangle(new Triangle3D(
						new Point3D(i*factor, j*factor, -10),
						new Point3D((i+1)*factor, (j+1)*factor, -10),
						new Point3D(i*factor, (j+1)*factor, -10)));
			}
		}
		
		// build the function terrain
		factor = 15;
		double step = 1.0;
		for(double x = -10.0; x <= 10.0; x += step){
			for(double y = -10.0; y <= 10.0; y += step){
				e.addTriangle(new Triangle3D(
						new Point3D(x*factor, y*factor, f(x, y)),
						new Point3D((x+step)*factor, y*factor, f(x+step, y)),
						new Point3D((x+step)*factor, (y+step)*factor, f(x+step, y+step))));
				e.addTriangle(new Triangle3D(
						new Point3D(x*factor, y*factor, f(x, y)),
						new Point3D((x+step)*factor, (y+step)*factor, f(x+step, y+step)),
						new Point3D(x*factor, (y+step)*factor, f(x, y+step))));

			}
		}
	}
	
	private double f(double x, double y){
		return 13 + 10*Math.sin(x*x + y*y);
	}

}
