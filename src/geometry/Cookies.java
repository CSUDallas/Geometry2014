package geometry;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

class Cookies {
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		double startTime = System.currentTimeMillis();
		int N = scanner.nextInt();
		scanner.nextLine();
		scanner.nextLine();
		for(int i = 0; i < N; i++){
			solveOneProblem();
			if(N < N-1)
				System.out.println("");
		}
		System.out.println("Time = " + (System.currentTimeMillis() - startTime));
	}

	public static void solveOneProblem(){
		Point2D.Double points[] = new Point2D.Double[10000];
		int numPoints = 0;
		int maxNumInside = 1;
		Point2D.Double bestX = null;

		// read a line
		String s = scanner.nextLine();
		while(s.trim().length() > 4){
			String xy[] = s.split(" ");
			double x = java.lang.Double.valueOf(xy[0]);
			double y = java.lang.Double.valueOf(xy[1]);
			points[numPoints] = new Point2D.Double(x, y);
			numPoints += 1;
			s = scanner.nextLine();
		}
		if(numPoints == 0)
			System.out.println("0");
		if(numPoints == 1)
			System.out.println("1");
		bestX = points[0];

		// Check each pair
		for(int i = 0; i < numPoints-1; i++){
			Point2D.Double P = points[i];
			for(int j = i+1; j < numPoints; j++){
				Point2D.Double Q = points[j];
				double sqdist = dist2(P, Q);
				if(sqdist > 25) // If too far apart
					continue;

				// First consider the circle to the left of the line PQ
				// For points X on the circle, on the left side of the line
				// the cosine of the angle is adj/hyp
				double leftSideCos = Math.sqrt(25-sqdist) / 5;
				double rightSideCos = -leftSideCos;

				// Now check all the points for in-circle-ness
				int numInside = 0;
				for(int k = 0; k < numPoints; k++){
					if(k == i || k == j){
						numInside++;
					} else {
						Point2D.Double X = points[k];
						double dotprod = ((Q.x - X.x)*(P.x-X.x) + (Q.y - X.y)*(P.y - X.y));
						double xcos = dotprod / Math.sqrt(dist2(X, Q) * dist2(X, P));
						if(sigma(P, Q, X) > 0 && xcos < leftSideCos) // point on left
							numInside++;
						if(sigma(P, Q, X) <= 0 && xcos < rightSideCos)
							numInside++;
					}
				}
				if(numInside > maxNumInside){
					maxNumInside = numInside;
					Point2D.Double v = new Point2D.Double(-Q.y + P.y, Q.x - P.x);
					double f = Math.sqrt((6.25 - sqdist/4)/sqdist);
					bestX = new Point2D.Double((P.x + Q.x)/2 + v.x*f, (P.y + Q.y)/2 + v.y*f);
				}

				// Now the circle on the right side.
				rightSideCos = Math.sqrt(25-sqdist) / 5;
				leftSideCos = -leftSideCos;

				// Now check all the points for in-circle-ness
				numInside = 0;
				for(int k = 0; k < numPoints; k++){
					if(k == i || k == j){
						numInside++;
					} else {
						Point2D.Double X = points[k];
						double dotprod = ((Q.x - X.x)*(P.x-X.x) + (Q.y - X.y)*(P.y - X.y));
						double xcos = dotprod / Math.sqrt(dist2(X, Q) * dist2(X, P));
						if(sigma(P, Q, X) > 0 && xcos < leftSideCos) // point on left
							numInside++;
						if(sigma(P, Q, X) <= 0 && xcos < rightSideCos)
							numInside++;
					}
				}
				if(numInside > maxNumInside){
					maxNumInside = numInside;
					Point2D.Double v = new Point2D.Double(Q.y - P.y, -Q.x + P.x);
					double f = Math.sqrt((6.25 - sqdist/4)/sqdist);
					bestX = new Point2D.Double((P.x + Q.x)/2 + v.x*f, (P.y + Q.y)/2 + v.y*f);
				}
			}
		}
		System.out.println(maxNumInside);
		//drawpic(numPoints, points, bestX);
	}

	// Returns the square of the distance between two points
	public static double dist2(Point2D.Double A, Point2D.Double B){
		return (B.x-A.x)*(B.x-A.x) + (B.y-A.y)*(B.y-A.y);
	}

	public static int sigma(Point2D.Double A, Point2D.Double B, Point2D.Double C){
		double val = (B.x - A.x) * (C.y - A.y) - (C.x - A.x) * (B.y - A.y);
		if(val < 0)         return -1;
		else if (val == 0)  return 0;
		else                return 1;
	}


	public static void drawpic(int n, Point2D.Double[] points, Point2D.Double C){
		int rad = 1;
		int fac = 10;
		int xoffset = 75;
		int yoffset = 100;
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("pic.ps", "UTF-8");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println("newpath");
		writer.println("/str 50 string def");
		writer.println("/Times-Roman 6 selectfont");
		for(int i = 0; i < n; i++){
			double x = points[i].x*fac + xoffset;
			double y = points[i].y*fac + yoffset;
			writer.printf("%.2f %.2f moveto\n", x + rad, y);
			writer.printf("%.2f %.2f %d %d %d arc\n", x, y, rad, 0, 360);
			writer.printf("0 3 rmoveto %d str cvs show\n", i);
		}
		writer.printf("%.2f %.2f moveto\n", ((C.x+2.5)*fac) + xoffset, (C.y*fac) + yoffset);
		writer.printf("%.2f %.2f %d %d %d arc\n", ((C.x)*fac) + xoffset, (C.y*fac) + yoffset, (int)(2.5*fac), 0, 360);
		writer.println("stroke");
		writer.println("showpage");
		writer.close();
	}
}
