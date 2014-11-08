package geometry;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

class CookiesThreaded {
	public static Scanner scanner = new Scanner(System.in);
	private static int MAX_NUM_CHIPS = 10000;
	private static int NUM_THREADS = 18;
	private static Semaphore sem = new Semaphore(0);
	private static int[] answers = new int[NUM_THREADS];
	private static Point2D.Double points[] = new Point2D.Double[MAX_NUM_CHIPS];
	private static int numPoints = 0;

	public static void main(String[] args) throws InterruptedException {
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

	public static void solveOneProblem() throws InterruptedException{

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

		// Check each pair
		for(int i = 0; i < NUM_THREADS; i++)
			launchThread(i);
		for(int j = 0; j < NUM_THREADS; j++)
			sem.acquire();
		int best = 0;
		for(int j = 0; j < NUM_THREADS; j++)
			if(answers[j] > best)
				best = answers[j];
		System.out.println("Best = " + best);

	}

	private static void launchThread(final int index){
		final int start = (index*numPoints)/NUM_THREADS;
		final int end   = ((index+1)*numPoints)/NUM_THREADS - 1;

		new Thread(){
			public void run(){
				System.out.println("Launched! " + index);
				int maxNumInside = 1;
				Point2D.Double bestX = null;
				if(numPoints == 0)
					System.out.println("0");
				if(numPoints == 1)
					System.out.println("1");
				bestX = points[0];

				for(int i = start; i < end; i++){
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
				answers[index] = maxNumInside;
				System.out.println("Done " + index);
				sem.release();
			}
		}.start();
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
	}}
