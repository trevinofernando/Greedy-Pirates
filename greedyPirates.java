import java.util.*;
import java.io.*;
import java.lang.*;

class GreedyPirates{
	/*
										Input Specification
		The first line of input will contain 4 integers x1, y1 , x2, and y2. The curtain's gap will be from (x1, y1) to (x2, y2). The curtain(and subsequently it's gap)will be parallel to the x-axis.
		The secondline of input will be singlepositiveintegersn(n< 100,000), representing the number of pirates. 
		The followingnlines will describe a pirate. 
		Each pirate description consists of 1 pair of integers representing the point of the pirate's chair on the 2-dimensionalCartesian grid.
		All y-coordinates will be positive, and no pirate will be between the curtain and the loot wall, or on the curtain.
		No coordinate value will be greater than 100,000 in magnitude.
		
										Output Specification
		The first and only line of output should contain a number represent the minimum number of meetings Luffus needs to prevent pirate disagreements.
	*/
	public static double gap1X;
	public static double gap1Y;
	public static double gap2X;
	public static double gap2Y;
	public static double bufferX;
	public static double bufferY;
	public static int numPirates;
	public static Pirate pirate;
	
	public static void main (String[] Args){
				
		Scanner sc = new Scanner(System.in);
		gap1X = sc.nextInt();
		gap1Y = sc.nextInt(); //should be equal to Gap2Y
		gap2X = sc.nextInt();
		gap2Y = sc.nextInt();
		numPirates = sc.nextInt();
		
		if(gap1X > gap2X){ //swap variables to have gap1X on the left
			bufferX=gap2X;
			gap2X=gap1X;
			gap1X=bufferX;
		}
		PriorityQueue<Pirate> pqLeft = new PriorityQueue<Pirate>(numPirates); //To be filled on the for loop
		PriorityQueue<Pirate> pqRight = new PriorityQueue<Pirate>(numPirates, byRight); //Empty after for loop
		//Adding pirates and calculate intervals
		for(int i = 0; i < numPirates; i++){
			bufferX = sc.nextInt();
			bufferY = sc.nextInt();
			pqLeft.add(new Pirate(xIntersept(gap1X,gap1Y,bufferX,bufferY), xIntersept(gap2X,gap2Y,bufferX,bufferY)));
		}
		pqRight.add(pqLeft.poll()); //add first pirate to answer
		while(!pqLeft.isEmpty()){
			if(pqRight.peek().right <= pqLeft.peek().left) //Can be in the same meeting
				pqRight.poll();//Replace privous pirate with new
			pqRight.add(pqLeft.poll()); //add pirate
		}
		System.out.println(pqRight.size());//print answer
	}
	//Find X intersept
	public static double xIntersept (double gapX, double gapY, double px, double py){
		return gapY * ((px - gapX ) / (gapY - py)) + gapX;
	}
	//Custom class to store pirates
	public static class Pirate implements Comparable<Pirate>{
		double left;
		double right;
		//Pirate constructor
		public Pirate(double left, double right){
			this.left = left;
			this.right = right;
		}
		
		public int compareTo(Pirate o){
			if(this.left - o.left < 0)
					return -1;
				return 1;
		}
	}
	//Custom compareator
	public static Comparator<Pirate> byRight = new Comparator<Pirate>(){
			public int compare(Pirate a, Pirate b) {
				if(a.right - b.right < 0)
					return -1;
				return 1;
			}
		};
}
