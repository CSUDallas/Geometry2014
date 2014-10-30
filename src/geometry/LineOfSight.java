package geometry;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LineOfSight {
	public static Scanner scanner = new Scanner(System.in);
	static double[] house = new double[3];
	static double[] property = new double[3];
	static double[][] obstacles;

	public static void main(String[] args) {
		
		house[0] = scanner.nextDouble();
		house[1] = scanner.nextDouble();
		house[2] = scanner.nextDouble();
		while(house[0] != 0 || house[1] != 0 || house[2] != 0){
			solveOneProblem();
			house[0] = scanner.nextDouble();
			house[1] = scanner.nextDouble();
			house[2] = scanner.nextDouble();
		}
	}
	
	public static void solveOneProblem(){
		property[0] = scanner.nextDouble();
		property[1] = scanner.nextDouble();
		property[2] = scanner.nextDouble();
		double IGNORE = 1342.18478934719;
		
		// Read the obstacles
		int N = scanner.nextInt();
		obstacles = new double[N][3];
		for(int i = 0; i < N; i++)
			for(int j = 0; j < 3; j++)
				obstacles[i][j] = scanner.nextDouble();

		// Process the obstacles
		double[] goods = new double[N+1];
		double[] bads  = new double[N+1];
		for(int i = 0; i < N; i++){
			if(obstacles[i][2] >= house[2] || obstacles[i][2] <= property[2]){
				goods[i] = IGNORE;
				bads[i] = IGNORE;
			} else {
				goods[i] = house[0] - (house[0] - obstacles[i][1])*(house[2] - property[2])/(house[2] - obstacles[i][2]);
				bads[i]  = house[1] - (house[1] - obstacles[i][0])*(house[2] - property[2])/(house[2] - obstacles[i][2]);
			}
		}
		goods[N] = property[0];
		bads[N]  = property[1];
		N = N + 1;
		Arrays.sort(goods);
		Arrays.sort(bads);
		int bi = 0, gi = 0; // indexes
		int score = -1;     // running visibility score
		double start = IGNORE;
		double maxLength = 0;
		for(int i = 0; i < 2*N; i++){
			if(gi < N && goods[gi] == IGNORE){
				gi++;
				continue;
			}
			if(bi < N && bads[bi] == IGNORE){
				bi++;
				continue;
			}
			if(gi >= N || (bi < N && bads[bi] < goods[gi])){
				score--;
				bi++;
			} else {
				score++;
				gi++;
			}
			if(score == 0){ // just became clear
				start = goods[gi-1];
			} else if(score == -1 && start != IGNORE){
				double length = bads[bi-1] - start;
				if(length > maxLength)
					maxLength = length;
				start = IGNORE;
			}
		}
		if(maxLength == 0)
			System.out.println("No View");
		else
			System.out.printf("%.2f\n", maxLength);
	}
}
