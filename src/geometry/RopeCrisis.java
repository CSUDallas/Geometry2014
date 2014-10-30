package geometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;

class RopeCrisis {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;

	public static void main(String[] args) throws IOException {

		st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		for(int i = 0; i < N; i++){
			solveOneProblem();
		}
	}

	public static void solveOneProblem() throws IOException{
		st = new StringTokenizer(br.readLine());
		double x1 = Double.parseDouble(st.nextToken());
		double y1 = Double.parseDouble(st.nextToken());
		double x2 = Double.parseDouble(st.nextToken());
		double y2 = Double.parseDouble(st.nextToken());
		double R  = Double.parseDouble(st.nextToken());

		double area = Math.abs(x1*y2 - x2*y1)/2;
		double R2   = R*R;
		double base2 = (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);

		if(4*area*area > R2*base2){
			System.out.printf("%.3f\n",Math.sqrt(base2));
			return;
		}


		double s12 = x1*x1 + y1*y1;
		double s22 = x2*x2 + y2*y2;
		double dotprod = x1*x2 + y1*y2;

		if(base2 + s22 < s12 || base2 + s12 < s22){
			System.out.printf("%.3f\n",Math.sqrt(base2));
			return;
		}

		double d1 = Math.sqrt(s12 - R2);
		double d2 = Math.sqrt(s22 - R2);
		double bigAngle = Math.acos(dotprod / (Math.sqrt(s12*s22)));
		double angle1 = Math.acos(R/Math.sqrt(s12));
		double angle2 = Math.acos(R/Math.sqrt(s22));
		double smallAngle = bigAngle - angle1 - angle2;
		double arc = R * smallAngle;
		System.out.printf("%.3f\n", d1 + d2 + arc);
	}


}
